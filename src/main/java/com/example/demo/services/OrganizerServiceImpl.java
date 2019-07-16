package com.example.demo.services;



import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Marathon;

import com.example.demo.model.Organizer;
import com.example.demo.model.Results;
import com.example.demo.model.User;
import com.example.demo.repo.MarathonRepo;
import com.example.demo.repo.OrganizerRepo;
import com.example.demo.repo.ResultsRepo;
import com.example.demo.repo.UserRepo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.demo.repo.MarathonRepo;

@Service
public class OrganizerServiceImpl implements OrganizerService{

	@Autowired
	MarathonRepo marathonRepo;
	@Autowired
	ResultsRepo resultsRepo;
	@Autowired
	UserRepo userRepo;
	@Autowired
	OrganizerRepo organizerRepo;
	
	
	
	
	
	@Override
	public boolean insertNewResult(Results results) {
		if(results == null) {
			return false;
		}
		Results resultTemp = resultsRepo.findByUserAndMarathonAndDisqualifiedAndTimeResult
				(results.getUser(), results.getMarathon(), results.isDisqualified(), results.getTimeResult());
		if(resultTemp != null) {
			return false;
		}else {
			resultsRepo.save(results);
			return false;
		}
	}
	
	
	@Override
	public boolean exportDataExcel()
	{
		//final String FILE_NAME = "MarathonExcel.xlsx";

	        XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("Marathon info");
	        
	        
	        ArrayList<Marathon> tempList = new ArrayList<Marathon>();
			for (Marathon m:marathonRepo.findAll())
			{
				tempList.add(m);
			}
			
	        int rowNum = 1;
	        System.out.println("Creating excel");
	        
	        Row row = sheet.createRow(rowNum++);
	        int colNum = 0;
	        Cell cellid = row.createCell(colNum++);
            cellid.setCellValue("Marathon ID");
            Cell celln = row.createCell(colNum++);
            celln.setCellValue("Name");
            Cell cellp = row.createCell(colNum++);
            cellp.setCellValue("Place");
            Cell celldi= row.createCell(colNum++);
            celldi.setCellValue("Distance");
            Cell cellda = row.createCell(colNum++);
            cellda.setCellValue("Date");
            Cell cellt= row.createCell(colNum++);
            cellt.setCellValue("Time");
	        
	        for (int i = 1; i <=tempList.size(); i++) {
				Marathon maratoni1 = tempList.get(i-1);
				row = sheet.createRow(rowNum++);
	            colNum = 0;
	            
	            Cell cell0 = row.createCell(colNum++);
                cell0.setCellValue(maratoni1.getID_mar());
	            Cell cell = row.createCell(colNum++);
	            cell.setCellValue(maratoni1.getName());
	            Cell cell2 = row.createCell(colNum++);
	            cell2.setCellValue(maratoni1.getPlace());
	            Cell cell3 = row.createCell(colNum++);
	            cell3.setCellValue(maratoni1.getDistance());
	            Cell cell4 = row.createCell(colNum++);
	            cell4.setCellValue(maratoni1.getDate());
	            Cell cell5 = row.createCell(colNum++);
	            cell5.setCellValue(maratoni1.getTime());
			}
	        
	        /*for (Marathon [] maratoni1 : maratoni) {
	            Row row = sheet.createRow(rowNum++);
	            int colNum = 0;
	            for (Object field : maratoni1) {
	                Cell cell = row.createCell(colNum++);
	                if (field instanceof String) {
	                    cell.setCellValue((String) field);
	                } else if (field instanceof Integer) {
	                    cell.setCellValue((Integer) field);
	                }
	            }
	        }
	        */

	        try {
	        	//File file = new File("D:/data/file.xlsx");
	        	File file = new File("/home/s8_spila_e/Desktop/WORKSPACE/Marathon3DontBreakThis-Eduards/src/main/resources/export/MarathonExport.xlsx");
	            FileOutputStream outputStream = new FileOutputStream(file);
	            //FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
	            workbook.write(outputStream);
	            workbook.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	      System.out.println("Done");
		
		
		return false;
	}
	
	@Override
	public void sendEmail(String orgemail) {}
	@Override
	public void sendWithAttach(String orgemail) {}

	@Override
	public Organizer findByLoginAndPassword(Organizer organizer) {
		Organizer oTemp = organizerRepo.findByLoginAndPassword(organizer.getLogin(), organizer.getPassword());
		if (oTemp != null)
			return oTemp;
		else 
			return null;
	}
	
	@Override
	public Organizer selectById_org(long id_org) {
		if (id_org >=0) {
		Organizer orgTemp = organizerRepo.findById(id_org).get();	
		if(orgTemp!=null)
			return orgTemp;
		}
		return null;
	}
	
	@Override
	public boolean updateOrganizerById_org(Organizer organizer, long id_org) {
		if (organizerRepo.existsById(id_org) && organizer!=null) {
			Organizer orgTemp = organizerRepo.findById(id_org).get();
			orgTemp.setName(organizer.getName());
			orgTemp.setLogin(organizer.getLogin());
			orgTemp.setPassword(organizer.getPassword());
			organizerRepo.save(orgTemp);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean deleteOrganizerById_org(long id_org) {
		if (organizerRepo.existsById(id_org))
		{
		Organizer orgTemp = organizerRepo.findById(id_org).get();
			organizerRepo.delete(orgTemp);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean addNewOrganizer(Organizer organizer) {
		if (organizer==null)
			return false;
		Organizer orgTemp = organizerRepo.findByLoginAndPassword(organizer.getLogin(), organizer.getPassword());
		if(orgTemp!=null) {
			return false;
		}
		else
		{	
			organizerRepo.save(organizer);
			return true;
		}
	}	
	
	@Override
	public boolean deleteOrganizerByObject(Organizer organizer) {
		if(organizerRepo.existsById(organizer.getId_org()) && organizer!=null)
		{
			organizerRepo.delete(organizer);
			return true;
		}
	return false;	
	
	
	
}


	@Override
	public boolean changeOrgPassword(Organizer organizer, long id) {
		Organizer orgTemp = organizerRepo.findById(id).get();
		if (orgTemp != null) {
			orgTemp.setPassword(organizer.getPassword());
			orgTemp.setFirstLogin(false);
			organizerRepo.save(orgTemp);
		}
		return false;
	}
	
	@Override
	public boolean organizerAlreadyExists(Organizer organizer) {
		Organizer orgTemp = organizerRepo.findByNameOrLoginOrOrgemail(organizer.getName(), organizer.getLogin(), organizer.getOrgemail());
		if (orgTemp == null)
			return false;
		else 
			return true;
	}
	
	@Override
	public boolean exportAllMarathonsExcel()
	{
		final String FILE_NAME = "MarathonsExcel.xlsx";

	        XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("Marathon info");
	        
	        ArrayList<Marathon> tempList = new ArrayList<Marathon>();
			for (Marathon m:marathonRepo.findAll())
			{
				tempList.add(m);
			}
			
	        int rowNum = 1;
	        System.out.println("Creating excel");
	        
	        Row row = sheet.createRow(rowNum++);
	        int colNum = 0;
	        Cell cellid = row.createCell(colNum++);
            cellid.setCellValue("Marathon ID");
            Cell celln = row.createCell(colNum++);
            celln.setCellValue("Name");
            Cell cellp = row.createCell(colNum++);
            cellp.setCellValue("Place");
            Cell celldi= row.createCell(colNum++);
            celldi.setCellValue("Distance");
            Cell cellda = row.createCell(colNum++);
            cellda.setCellValue("Date");
            Cell cellt= row.createCell(colNum++);
            cellt.setCellValue("Time");
	        
	        for (int i = 1; i <=tempList.size(); i++) {
				Marathon maratoni1 = tempList.get(i-1);
				row = sheet.createRow(rowNum++);
	            colNum = 0;
	            
	            Cell cell0 = row.createCell(colNum++);
                cell0.setCellValue(maratoni1.getID_mar());
	            Cell cell = row.createCell(colNum++);
	            cell.setCellValue(maratoni1.getName());
	            Cell cell2 = row.createCell(colNum++);
	            cell2.setCellValue(maratoni1.getPlace());
	            Cell cell3 = row.createCell(colNum++);
	            cell3.setCellValue(maratoni1.getDistance());
	            Cell cell4 = row.createCell(colNum++);
	            cell4.setCellValue(maratoni1.getDate());
	            Cell cell5 = row.createCell(colNum++);
	            cell5.setCellValue(maratoni1.getTime());
			}

	        try {
	        	//File file = new File("D:/data/file.xlsx");
	        	//File file = new File("MarathonInfoSystem-master/src/main/resources/file.xlsx");
	            //FileOutputStream outputStream = new FileOutputStream(file);
	            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
	            workbook.write(outputStream);
	            workbook.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    System.out.println("Done");
		return false;
	}
	
	@Override
	public boolean exportOneMarathonExcel(long id)
	{
		final String FILE_NAME = "MarathonExcel.xlsx";

	        XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("Marathon results");
			
			ArrayList<Results> tempRes = new ArrayList<Results>();
			for (Results r:resultsRepo.findAll())
			{
				if(r.getMarathon().getID_mar()==id)
				tempRes.add(r);
			}
			
	        int rowNum = 1;
	        System.out.println("Creating excel");
	        
	        Row row = sheet.createRow(rowNum++);
	        int colNum = 0;
            Cell celln = row.createCell(colNum++);
            celln.setCellValue("Name");
            Cell cellp = row.createCell(colNum++);
            cellp.setCellValue("Surname");
            Cell celldi= row.createCell(colNum++);
            celldi.setCellValue("BirthDate");
            Cell cellda = row.createCell(colNum++);
            cellda.setCellValue("Distance");
            Cell cellt= row.createCell(colNum++);
            cellt.setCellValue("Result");
            Cell cellm = row.createCell(colNum++);
            cellm.setCellValue("Disq");
	        
	        for (int i = 1; i <=tempRes.size(); i++) {
				Results results1 = tempRes.get(i-1);
				row = sheet.createRow(rowNum++);
	            colNum = 0;
	            
	      
	            Cell cell1 = row.createCell(colNum++);
	            cell1.setCellValue(results1.getUser().getName());
	            Cell cell2 = row.createCell(colNum++);
	            cell2.setCellValue(results1.getUser().getSurname());
	            Cell cell3 = row.createCell(colNum++);
	            cell3.setCellValue(results1.getUser().getBirthDate());
	            Cell cell4 = row.createCell(colNum++);
	            cell4.setCellValue(results1.getMarathon().getDistance());
	            Cell cell5 = row.createCell(colNum++);
	            cell5.setCellValue(results1.getTimeResult());
	            Cell cell6 = row.createCell(colNum++);
	            cell6.setCellValue(results1.isDisqualified());
			}

	        try {
	        	//File file = new File("D:/data/file.xlsx");
	        	//File file = new File("MarathonInfoSystem-master/src/main/resources/file.xlsx");
	            //FileOutputStream outputStream = new FileOutputStream(file);
	            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
	            workbook.write(outputStream);
	            workbook.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    System.out.println("Done");
		return false;
	}
	
	@Override
	public boolean exportMarathonsExcel(long id, ArrayList<Marathon> marList)
	{
		final String FILE_NAME = "MarathonExcel.xlsx";
		XSSFWorkbook workbook = new XSSFWorkbook();
		for(Marathon m: marList)
		{
			XSSFSheet sheet = workbook.createSheet(m.getName());
			
			
	        int rowNum = 1;
	        System.out.println("Creating excel");
	        
	        Row row = sheet.createRow(rowNum++);
	        int colNum = 0;
            Cell celln = row.createCell(colNum++);
            celln.setCellValue("Name");
            Cell cellp = row.createCell(colNum++);
            cellp.setCellValue("Surname");
            Cell celldi= row.createCell(colNum++);
            celldi.setCellValue("BirthDate");
            Cell cellda = row.createCell(colNum++);
            cellda.setCellValue("Distance");
            Cell cellt= row.createCell(colNum++);
            cellt.setCellValue("Result");
            Cell cellm = row.createCell(colNum++);
            cellm.setCellValue("Disq");
	        
            for (Results r:findAllResultsByMarathon(m))
			{ 
				row = sheet.createRow(rowNum++);
	            colNum = 0;
	            
	  
	            Cell cell = row.createCell(colNum++);
	            cell.setCellValue(r.getUser().getName());
	            Cell cell2 = row.createCell(colNum++);
	            cell2.setCellValue(r.getUser().getSurname());
	            Cell cell3 = row.createCell(colNum++);
	            cell3.setCellValue(r.getUser().getBirthDate());
	            Cell cell4 = row.createCell(colNum++);
	            cell4.setCellValue(r.getMarathon().getDistance());
	            Cell cell5 = row.createCell(colNum++);
	            cell5.setCellValue(r.getTimeResult());
	            Cell cell6 = row.createCell(colNum++);
	            cell6.setCellValue(r.isDisqualified());
			}
			}
	        try {
	        	//File file = new File("D:/data/file.xlsx");
	        	//File file = new File("MarathonInfoSystem-master/src/main/resources/file.xlsx");
	            //FileOutputStream outputStream = new FileOutputStream(file);
	            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
	            workbook.write(outputStream);
	            workbook.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    System.out.println("Done");
		return false;
	}

	@Override
	public ArrayList<Results> findAllResultsByMarathon(Marathon maraton) {
		// TODO Auto-generated method stub
		
		ArrayList<Results> resultTemp= resultsRepo.findByMarathon(maraton);
		if (resultTemp==null)
		{
			return new ArrayList<Results>();
		}
		else return resultTemp;
	}
	
	@Override
	public ArrayList<Marathon> findMarathonByOrganizerById(long id) {
		// TODO Auto-generated method stub
		
		Organizer tempOrg =organizerRepo.findById(id).get();
		if(tempOrg!=null) {
			ArrayList<Marathon> tempMarathon= marathonRepo.findByOrganizer(tempOrg);
			if(tempMarathon!=null)
			return tempMarathon;
		}
		
		ArrayList<Marathon> emptyMarathon= new ArrayList<Marathon>();
		return emptyMarathon;
	}
	
}