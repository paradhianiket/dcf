package dcf.script.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Properties;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader extends CommonUtil
{
	public static String filename="CaseSystemTestData";
	public static FileInputStream fis=null;
	public static FileOutputStream outputStream=null;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static XSSFRow Row;
	public static XSSFCell Cell;
	public static String datavalue;
	public static Object myObj;
	public static Object myObj1;
	public static String sheetName=null;
	
	
// returns sheet number
		public int getsheetnumber()
		{
			int sheetnumber = workbook.getNumberOfSheets();
			return sheetnumber;
		}
// returns rownumber
		public int getrownumber()
		{
			int rowcount;
			rowcount=sheet.getPhysicalNumberOfRows();
			return rowcount;
		}	
// returns colnumber
		public int getcolnumber()
		{
			int colcount;
			colcount=sheet.getRow(0).getLastCellNum()-sheet.getRow(0).getFirstCellNum();
			System.out.println(colcount);
			return colcount;
		}
		
//returns the data from a cell
		public String getCellData(String sheetName)
		{
			String tdFileName = properties.getProperty(filename);
			File file = new File(TESTDATA_FOLDER + tdFileName);
		try
		{
			fis = new FileInputStream(file);
			workbook = new XSSFWorkbook(fis);
			for(int i=0; i<getsheetnumber(); i++)
			{
				sheet = workbook.getSheetAt(i);
				sheetName=sheet.getSheetName();
				//System.out.println("Row and cloumn count for sheetname "+sheetName+" are rowcount : "+getrownumber()+ " and columncount :"+getcolnumber());
				for(int j=0; j<getcolnumber(); j++)
				{
					String data = sheet.getRow(0).getCell(j).getStringCellValue();
					if(data.equalsIgnoreCase("RunMode"))
					{
						for(int k=1; k<getrownumber(); k++)
						{
							String runmodevalue= sheet.getRow(k).getCell(j).getStringCellValue();
							Row = sheet.getRow(k);
							for(int m=0; m<getcolnumber(); m++)
									{
										if(runmodevalue.equalsIgnoreCase("Y"))
										{
											Cell= Row.getCell(m);
											String datavalue=Row.getCell(m).getStringCellValue();
										}
									}
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return datavalue;
		}
		
//to write data
		public void outdata(String sheetName) throws IOException
		{
			myObj= LocalDate.now();
			myObj1= LocalTime.now();
			for(int i=0; i<getsheetnumber(); i++)
			{
				sheet = workbook.getSheetAt(i);
				sheetName=sheet.getSheetName();
				sheet.getRow(0).createCell(getcolnumber()).setCellValue("Result_on_"+myObj+"_"+myObj1);
				for(int j=0; j<getcolnumber(); j++)
				{
					String data = sheet.getRow(0).getCell(j).getStringCellValue();
					if(data.equalsIgnoreCase("RunMode"))
					{
						for(int k=1; k<getrownumber(); k++)
						{
							String runmodevalue= sheet.getRow(k).getCell(j).getStringCellValue();
							Row = sheet.getRow(k);
							for(int m=0; m<getcolnumber(); m++)
									{
										if(runmodevalue.equalsIgnoreCase("Y"))
										{
											sheet.getRow(k).createCell(getcolnumber()).setCellValue("Pass");
										}
										else
										{
											sheet.getRow(k).createCell(getcolnumber()).setCellValue("Fail");
										}
									}
						}
					}
				}
			}
			outputStream= new FileOutputStream(filename);
			workbook.write(outputStream);
			workbook.close();
		}		
}
