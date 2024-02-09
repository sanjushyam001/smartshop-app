package com.smartshop.admin.user;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.smartshop.common.entity.User;

public class UserCsvExporter extends AbstractExporter {

	public void export(List<User> users,HttpServletResponse response) throws Exception  {
		
		super.setResponseHeader(response, "text/csv",".csv");
		
		ICsvBeanWriter csvWriter=new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		String[] csvHeader= {"User ID","E-mail","First Name","Last Name","Roles","Enabled"};
		String[] fieldMapping= {"id","email","firstName","lastName","roles","enabled"};
		csvWriter.writeHeader(csvHeader);
		users.forEach(user->{
			try {
				csvWriter.write(user, fieldMapping);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		csvWriter.close();
	
	}
}
