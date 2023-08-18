package com.paloit.drools.controller;

import com.paloit.drools.model.Address;
import com.paloit.drools.model.fact.AddressCheckResult;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.annotation.Resource;


@RequestMapping("/test")
@Controller
public class TestController {

    @Resource
    private KieContainer kieContainer;

    @ResponseBody
    @RequestMapping("/address")
    public void test(int num) {
        Address address = new Address();
        address.setPostcode(generateRandom(num));
        KieSession kieSession = kieContainer.newKieSession();

        AddressCheckResult result = new AddressCheckResult();
        kieSession.insert(address);
        kieSession.insert(result);
        int ruleFiredCount = kieSession.fireAllRules();
        kieSession.destroy();
        System.out.println(ruleFiredCount + " rule fullfiled");

        if (result.isPostCodeResult()) {
            System.out.println("Succesful rule verification");
        }

    }

    /**
     * Random number generator
     *
     * @param num
     * @return
     */
    public String generateRandom(int num) {
        String chars = "0123456789";
        StringBuffer number = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int rand = (int) (Math.random() * 10);
            number = number.append(chars.charAt(rand));
        }
        return number.toString();
    }
}