package jobs;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateCalculator {
	public static String calculate(String dateString)
	{
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		 LocalDateTime now = LocalDateTime.now();
		 
		 if(dateString.contains("hour"))
		 {
			 String numberOnly= dateString.replaceAll("[^0-9]", "");
			 long toMinus = Integer.parseInt(numberOnly);
			 now = now.minusHours(toMinus);
		 }
		 else if(dateString.contains("day"))
		 {
			 String numberOnly= dateString.replaceAll("[^0-9]", "");
			 long toMinus = Integer.parseInt(numberOnly);
			 now = now.minusDays(toMinus);
		 }
		 
		 String date = dtf.format(now);
		 return date;
		 
	}
}
