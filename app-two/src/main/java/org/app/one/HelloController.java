package org.app.one;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @GetMapping("long")
    public void longConnect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	PrintWriter out = resp.getWriter();
        out.println("进入Servlet的时间：" + new Date() + ".");
        out.flush();

        //在子线程中执行业务调用，并由其负责输出响应，主线程退出
        final AsyncContext ctx = req.startAsync();
        ctx.setTimeout(200000);
        new Work(ctx).start();
        out.println("结束Servlet的时间：" + new Date() + ".");
        out.flush();


    }

}

class Work extends Thread{
	private AsyncContext context;
	
	public Work(AsyncContext context){
		this.context = context;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(2000);//让线程休眠2s钟模拟超时操作
			PrintWriter wirter = context.getResponse().getWriter();			
			wirter.write("延迟输出");
			wirter.flush();
			context.complete();
		} catch (InterruptedException e) {
			
		} catch (IOException e) {
			
		}
	}
}
