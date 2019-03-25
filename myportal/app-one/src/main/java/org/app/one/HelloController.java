package org.app.one;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final Logger logger = Logger.getLogger(HelloController.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/hello")
    public String hello() {
        ServiceInstance instance = discoveryClient.getLocalServiceInstance();
        System.out.println("********************************************");
        System.out.println("* request path: "+instance.getUri() + " host: "+instance.getHost()+" serviceid: "+instance.getServiceId());
        System.out.println("********************************************");
        try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("work.done");
        return "Eureka-应用1";
    }
    
    @GetMapping("/getInfo")
    public String getInfo() {
    	return "getInfo";
    }

}