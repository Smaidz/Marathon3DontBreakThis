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
	public ArrayList<User> selectAllUsers() {
		ArrayList<User> tempList = new ArrayList<User>();
		for (User o:userRepo.findAll())
		{
			tempList.add(o);
		}
		return tempList;
	}
	
	@Override
	public boolean insertNewMarathon(Marathon marathon) {
		if(marathon == null) {
			return false;
		}
		Marathon marathonTemp = marathonRepo.findByNameAndDistanceAndPlaceAndDateAndTime(marathon.getName(), marathon.getDistance(), marathon.getPlace(), marathon.getDate(), marathon.getTime());
		if(marathonTemp != null) {
			return false;
		}else {
			marathonRepo.save(marathon);
			return false;
		}
	}
	
	@Override
	public boolean insertNewMarathon(long id, Marathon marathon) {
		if(marathon == null) {
			return false;
		}
		Marathon marathonTemp = marathonRepo.findByNameAndDistanceAndPlaceAndDateAndTime(marathon.getName(), marathon.getDistance(), marathon.getPlace(), marathon.getDate(), marathon.getTime());
		if(marathonTemp != null) {
			return false;
		} else {
			Organizer organizer = organizerRepo.findById(id).get();
			marathon.setOrganizer(organizer);
			marathonRepo.save(marathon);
			return false;
		}
	}
	
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
	public Marathon selectById(long id) {
		
		//FIND ONE
		Marathon carTemp  = marathonRepo.findById(id).get();
		if(carTemp != null) {
			return carTemp;
		}
		
		return null;
	}
	@Override
	public boolean updateMarathonById(Marathon marathon, long id) {
		if(marathonRepo.existsById(id) && marathon != null) {
			Marathon marathonUpdate = marathonRepo.findById(id).get();
			marathonUpdate.setName(marathon.getName());
			marathonUpdate.setDistance(marathon.getDistance());
			marathonUpdate.setPlace(marathon.getPlace());
			marathonUpdate.setDate(marathon.getDate());
			marathonUpdate.setTime(marathon.getTime());
			marathonRepo.save(marathonUpdate);
			return true;
		}
		return false;
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

	
}