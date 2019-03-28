package org.app.one;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {


    @GetMapping("down")
    public void longConnect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	XSSFWorkbook wb = new XSSFWorkbook();
    	XSSFSheet sheet = wb.createSheet();
    	OutputStream out = resp.getOutputStream();
		String excelName = java.net.URLEncoder.encode("终端激励查询", "UTF-8");
		resp.setContentType("application/vnd.ms-excel;charset=utf-8");
		resp.setHeader("Content-Disposition", "attachment;filename=" + excelName + ".xlsx");
		wb.write(out);
		out.flush();
		
        //在子线程中执行业务调用，并由其负责输出响应，主线程退出
        final AsyncContext ctx = req.startAsync();
        ctx.setTimeout(200000);
        new SecondFile(ctx).start();
       


    }

}

class SecondFile extends Thread{
	private AsyncContext context;
	
	public SecondFile(AsyncContext context){
		this.context = context;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(2000);//让线程休眠2s钟模拟超时操作
			XSSFWorkbook wb = new XSSFWorkbook();
	    	XSSFSheet sheet = wb.createSheet();
	    	HttpServletResponse resp=(HttpServletResponse) context.getResponse();
	    	OutputStream out = resp.getOutputStream();
			String excelName = java.net.URLEncoder.encode("终端激励查询2", "UTF-8");
			resp.setContentType("application/vnd.ms-excel;charset=utf-8");
			resp.setHeader("Content-Disposition", "attachment;filename=" + excelName + ".xlsx");
			wb.write(out);
			out.flush();
			context.complete();
		} catch (InterruptedException e) {
			
		} catch (IOException e) {
			
		}
	}
}
