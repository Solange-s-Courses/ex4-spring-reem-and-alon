//package com.example.ex4.components;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ScheduledTasks {
//
//    @Autowired
//    @Qualifier("paymentJob")
//    private TransactionJob paymentJob;
//
//    @Autowired
//    @Qualifier("refundJob")
//    private TransactionJob refundJob;
//
//    @Scheduled(cron = "0 0/2 * * * ?") // כל 2 דקות
//    public void runPaymentJob() {
//        paymentJob.run();
//    }
//
//    @Scheduled(cron = "0 1/2 * * * ?") // כל 2 דקות, אבל offset של דקה
//    public void runRefundJob() {
//        refundJob.run();
//    }
//}
