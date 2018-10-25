package com.myassignment;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ReadWebPage extends Ass1 {

    private static List<Ass1> data = new ArrayList<Ass1>();
    private static String website = "https://ms.wikipedia.org/wiki/Malaysia";

    public ReadWebPage(String data, String info) {
        super(data, info);
    }

    public static void collectData() {

        try {
            System.out.println("Accessing " + website + "..." + "\n");
            Document doc = Jsoup.connect(website).get();

            Element table = doc.select("table").get(5);
            Elements rows = table.select("tr");

            for (Element row : rows) {
                Elements data1 = row.select("th");
                Elements data2 = row.select("td");

                String column1 = data1.text();
                String column2 = data2.text();

                data.add(new Ass1(column1, column2));
            }

            System.out.println("Data have been collect" + "\n");
            Thread.sleep(3000);

            for(Ass1 data : data) {
                System.out.println(data.getData()+"   :   "+data.getInfo());
            }

        } catch (IOException e) {
            System.out.println("Can't access the data");

        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

        public static void writeToExcel () {

            String excel = "File.xlsx";

            System.out.println("\n"+"Writing the " + excel + "..." + "\n");


            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("Trivia");

            try {
                for (int i = 0; i < data.size(); i++) {
                    XSSFRow row = sheet.createRow(i);

                    XSSFCell cell1 = row.createCell(0);
                    cell1.setCellValue(data.get(i).getData());
                    XSSFCell cell2 = row.createCell(1);
                    cell2.setCellValue(data.get(i).getInfo());

                    sheet.autoSizeColumn(0);
                    sheet.autoSizeColumn(1);
                }

                FileOutputStream outfile = new FileOutputStream(excel);
                wb.write(outfile);
                outfile.close();
                Thread.sleep(3000);
                System.out.println("Data from " + website + "is successfully write");

            } catch (IOException e) {
                System.out.println("Can't write the data");
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            if (data.isEmpty()) {
                System.out.println("Data is not write");
                System.exit(0);
            }
        }

    }
