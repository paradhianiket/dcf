package dcf.script.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
	public static Object[][] datavalue=null;
	public static Object myObj;
	public static Object myObj1;
	public static String sheetName=null;
	public static int rowcount=0;
	public static int colcount=0;
	
//string fis
public FileInputStream fisexcelreader() 
{
	String tdFileName = properties.getProperty(filename);
	File filename1 = new File(TESTDATA_FOLDER + tdFileName);
		try
		{
			fis = new FileInputStream(filename1);
			workbook = new XSSFWorkbook(fis);
			fis.close();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fis;
}
	
// returns sheet number
		public int getsheetnumber()
		{
			int sheetnumber = workbook.getNumberOfSheets();
			return sheetnumber;
		}
// returns rownumber
		public int getRowCount(String sheetName)
		{
			sheet = workbook.getSheet(sheetName);
			rowcount=sheet.getPhysicalNumberOfRows();
			//System.out.println(rowcount);
			return rowcount;
		}	
// returns colnumber
		public int getColumnCount(String sheetName)
		{
			sheet = workbook.getSheet(sheetName);
			Row = sheet.getRow(0);
			//colcount=Row.getLastCellNum();
			colcount=sheet.getRow(0).getLastCellNum()-sheet.getRow(0).getFirstCellNum();
			//System.out.println(colcount);
			return colcount;
		}
//return sheetname
		public String sheetname()
		{
			fisexcelreader();
			for(int i=0; i<getsheetnumber(); i++)
			{
				sheet = workbook.getSheetAt(i);
				sheetName=sheet.getSheetName();
			}
			return sheetName;
			
		}

//returns the data from a cell
		public Object[][] getCellData(String sheetName,String colName)
		{
			fisexcelreader();
			try
			{
				int index = workbook.getSheetIndex(sheetName);
				sheet = workbook.getSheetAt(index);
				int ci=0;
				datavalue=new Object[getRowCount(sheetName)][getColumnCount(sheetName)];
				for(int i=0; i<getRowCount(sheetName); i++, ci++)
				{
					int cj=0;
					for(int j=0; j<getColumnCount(sheetName); j++, cj++)
					{
						datavalue[ci][cj]=sheet.getRow(i).getCell(j).getStringCellValue();
						//System.out.println(datavalue[ci][cj]);
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return datavalue;
		}	
}
