package org.hedwig.core.validator;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hedwig.core.context.Context;
import org.hedwig.core.context.Parameters;
import org.hedwig.core.dispatcher.InterceptorChain;
import org.hedwig.core.interceptor.Interceptor;
import org.hedwig.textutils.TextUtil;

public abstract class Validator implements Interceptor {

	private static final String emailAddressPattern = "\\b(^['_A-Za-z0-9-]+(\\.['_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";

	private static final String datePattern = "yyyy-MM-dd";
	
	private Parameters parameters;
	
	private Context context;
	
	private Map<String, String> paraErrorMsg;

	private boolean isInvalid = false;

	public void intercept(InterceptorChain chain) {
		this.context = chain.getContext();
		this.parameters = this.context.getParameters();

		this.paraErrorMsg = context.getAttribute("paraErrorMsg", Context.REQUEST_SCOPE);
		if (this.paraErrorMsg == null) {
			this.paraErrorMsg = new HashMap<String, String>();
		}
		
		validate();

		this.context.setAttribute("paraErrorMsg", paraErrorMsg, Context.REQUEST_SCOPE);
		
		if (isInvalid) {
			onError();
		} else {
			chain.invoke();
		}
	}

	/**
	 * Add message when validate failure.
	 */
	protected void addError(String errorKey, String errorMessage) {
		isInvalid = true;
		paraErrorMsg.put(errorKey, errorMessage);
	}

	/**
	 * Validate Required.
	 */
	protected void validateRequired(String field, String errorMessage) {
		String value = parameters.getString(field);
		if (TextUtil.isblank(value))
			addError(field, errorMessage);
	}
	
	/**
	 * Validate integer.
	 */
	protected void validateInteger(String field, int min, int max, String errorMessage) {
		String value = parameters.getString(field);
		try {
			int temp = Integer.parseInt(value);
			if (temp < min || temp > max)
				addError(field, errorMessage);
		}
		catch (Exception e) {
			addError(field, errorMessage);
		}
	}
	
	/**
	 * Validate integer.
	 */
	protected void validateInteger(String field, String errorMessage) {
		String value = parameters.getString(field);
		try {
			Integer.parseInt(value);
		}
		catch (Exception e) {
			addError(field, errorMessage);
		}
	}
	
	/**
	 * Validate long.
	 */
	protected void validateLong(String field, long min, long max, String errorMessage) {
		try {
			String value = parameters.getString(field);
			long temp = Long.parseLong(value);
			if (temp < min || temp > max)
				addError(field, errorMessage);
		}
		catch (Exception e) {
			addError(field, errorMessage);
		}
	}
	
	/**
	 * Validate long.
	 */
	protected void validateLong(String field, String errorMessage) {
		try {
			String value = parameters.getString(field);
			Long.parseLong(value);
		}
		catch (Exception e) {
			addError(field, errorMessage);
		}
	}

	/**
	 * Validate double.
	 */
	protected void validateDouble(String field, double min, double max, String errorMessage) {
		try {
			String value = parameters.getString(field);
			double temp = Double.parseDouble(value);
			if (temp < min || temp > max)
				addError(field, errorMessage);
		}
		catch (Exception e) {
			addError(field, errorMessage);
		}
	}
	
	/**
	 * Validate double.
	 */
	protected void validateDouble(String field, String errorMessage) {
		try {
			String value = parameters.getString(field);
			Double.parseDouble(value);
		}
		catch (Exception e) {
			addError(field, errorMessage);
		}
	}
	
	/** 
	 * Validate date.
	 */
	protected void validateDate(String field, Date min, Date max, String errorMessage) {
		try {
			String value = parameters.getString(field);
			Date temp = new SimpleDateFormat(datePattern).parse(value);	// Date temp = Date.valueOf(value); Ϊ�˼��� 64λ JDK
			if (temp.before(min) || temp.after(max))
				addError(field, errorMessage);
		}
		catch (Exception e) {
			addError(field, errorMessage);
		}
	}
	
	protected void validateDate(String field, String min, String max, String errorMessage) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
			validateDate(field, sdf.parse(min), sdf.parse(max), errorMessage);
		} catch (ParseException e) {
			addError(field, errorMessage);
		}
	}
	
	/**
	 * Validate equal field. Usually validate password and password again
	 */
	protected void validateEqualField(String field_1, String field_2, String errorMessage) {
		String value_1 = parameters.getString(field_1);
		String value_2 = parameters.getString(field_2);
		if (value_1 == null || value_2 == null || (! value_1.equals(value_2))) {
			addError(field_1, errorMessage);
			addError(field_2, errorMessage);
		}
	}
	
	/**
	 * Validate email.
	 */
	protected void validateEmail(String field, String errorMessage) {
		validateRegex(field, emailAddressPattern, false, errorMessage);
	}
	
	/**
	 * Validate URL.
	 */
	protected void validateUrl(String field, String errorMessage) {
		try {
			String value = parameters.getString(field);
			if (value.startsWith("https://"))
				value = "http://" + value.substring(8);
			new URL(value);
		} catch (MalformedURLException e) {
			addError(field, errorMessage);
		}
	}
	
	/**
	 * Validate regular expression.
	 */
	protected void validateRegex(String field, String regExpression, boolean isCaseSensitive, String errorMessage) {
		String value = parameters.getString(field);
        if (value == null) {
        	addError(field, errorMessage);
        	return ;
        }
        Pattern pattern = isCaseSensitive ? Pattern.compile(regExpression) : Pattern.compile(regExpression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches())
        	addError(field, errorMessage);
	}
	
	/**
	 * Validate regular expression and case sensitive.
	 */
	protected void validateRegex(String field, String regExpression, String errorMessage) {
		validateRegex(field, regExpression, true, errorMessage);
	}
	
	protected void validateString(String field, boolean notBlank, int minLen, int maxLen, String errorMessage) {
		String value = parameters.getString(field);
		if (value == null || value.length() < minLen || value.length() > maxLen) 
			addError(field, errorMessage);
		else if(notBlank && "".equals(value.trim()))
			addError(field, errorMessage);
	}
	
	/**
	 * Validate string.
	 */
	protected void validateString(String field, int minLen, int maxLen, String errorMessage) {
		validateString(field, true, minLen, maxLen, errorMessage);
	}
	
	protected Context getContext() {
		return context;
	}

	public abstract void validate();
	
	public abstract void onError();

}
