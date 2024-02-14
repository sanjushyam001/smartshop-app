package com.smartshop.admin.user.export;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import  com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfWriter;
import com.smartshop.common.entity.User;



public class UserPdfExporter extends AbstractExporter {

	public void export(List<User> listUsers,HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "application/pdf", ".pdf");
		
		Document document =new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		//font.setColor(Color.BLUE);
		Paragraph paragraph=new Paragraph("List of Users",font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(paragraph);
		PdfPTable table=new PdfPTable(6);
		table.setWidthPercentage(100f);
		table.setSpacingBefore(10);
		
		table.setWidths(new float[] {1.5f,3.5f,3.0f,3.0f,3.0f,1.5f});
		
		writeTableHeader(table);
		
		writeTableData(table,listUsers);
		document.add(table);
		document.close();
		
	}

	

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell=new PdfPCell();
		//cell.setBackgroundColor(color);
		cell.setPadding(10);
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(8);
		cell.setPhrase(new Phrase("User ID",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Email",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("First Name",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Last Name",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Roles",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Enabled",font));
		table.addCell(cell);
		
	}
	
	private void writeTableData(PdfPTable table, List<User> listUsers) {
		
		PdfPCell cell=new PdfPCell();
		//cell.setBackgroundColor(color);
		cell.setPadding(5);
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(7);
		
		for(User user:listUsers) {
			
			cell.setPhrase(new Phrase(String.valueOf(user.getId()),font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(user.getEmail(),font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(user.getFirstName(),font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(user.getLastName(),font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase(user.getRoles().toString(),font));
			table.addCell(cell);
			
		
			cell.setPhrase(new Phrase(String.valueOf(user.isEnabled()),font));
			table.addCell(cell);
			
		}
		
	}
}
