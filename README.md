# coursemicroservice
Course micro service

Description : Designed course and student microservices as per CodingTest-Microservice.pdf

1) Cluster mongodb
   ======================
    * https://cloud.mongodb.com/user#/atlas/login
    * create account 
    * create database : coursedb
    * create document : course
    * document will be added automatically by spring bootstrap class   

      Sample
      -------
      {"_id":{"$oid":"5ded9404529f3268634654c4"},"courseId":{"$numberInt":"1"},"title":"ML","description":"Machine   Learning","active":true,"fee":{"$numberDouble":"1500"},"_class":"com.example.demo.document.Course"}

2) RabbitMQ
   =============
    * Course micro service will be sender/publisher to queue StudentAQ and StudentRQ
    * Install RabbitMQServer from official website. 
    * After installation check service status in start-> run -> services.msc
    * Observation: Status: Running, Startup type: Automatic
    * Note:The pre-requisite for RabbitMQ is Erlang. (Install OTP_win64_19.3.exe from softwares folder
   
      Run rabbitmq plugin to view concole
      --------------------------------
       *  D:\Program Files\RabbitMQ Server\rabbitmq_server-3.6.9\sbin>rabbitmq-plugins enable rabbitmq_management
       *  login local rabbitmq admin console @ http://localhost:15672
      user:guest
      pass:guest

3) Run spring boot application by maven run configuration
   =========================================================

       * clean install spring-boot:run


4) Verify the output via POSTMAN
   =================================

         * GET METHOD    : http://localhost:8081/course/get
         * DELETE METHOD : http://localhost:8081/course/remove?courseID=4              
         * POST METHOD   : http://localhost:8081/course/add/
                 * REQUEST PAYLOAD :  {  "courseId": 4,  "title": "AWS",  "description": "Amazon Web Service,  "active": true,  "fee": 900 }
                 * Content-type : application/json


5) Content Negotiation end-points
   ===============================
   * Using URL suffixes (extensions) in the request (eg .xml/.json)
   * Using URL parameter in the request (eg ?format=json)
   * Using Accept header in the request
   
By default, this is the order in which the Spring content negotiation manager will try to use these three strategies. And if none of these are enabled, we can specify a fallback to a default content type of json.

6) API Documentation
   ====================
   swagger core is implemented and available @ http://localhost:8081/v2/api-docs