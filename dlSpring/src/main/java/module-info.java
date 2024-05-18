module com.example.dlSpring {
    requires spring.security.config;
    requires spring.context;
    requires spring.beans;
    requires spring.security.crypto;
    requires spring.security.web;
    requires spring.security.core;
    requires spring.web;
    requires jakarta.persistence;
    requires spring.data.jpa;
    requires spring.tx;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.core;
    requires static lombok;
    requires jakarta.annotation;
    requires com.fasterxml.jackson.annotation;
    requires org.hibernate.orm.core;
    requires java.base;
    requires spring.aop;
    requires org.apache.tomcat.embed.core;
    requires jjwt;
    requires org.slf4j;
    requires spring.boot.starter.data.jpa;

    opens com.example.dlSpring to spring.core, com.example.dlFx;
    opens com.example.dlSpring.config to spring.core, spring.beans, spring.context;
    opens com.example.dlSpring.model to org.hibernate.orm.core, spring.core, com.example.dlFx;
    opens com.example.dlSpring.dto to spring.core, com.example.dlFx;
    opens com.example.dlSpring.service to spring.beans, spring.core;
    opens com.example.dlSpring.controller to spring.beans, spring.core, spring.web;
    opens com.example.dlSpring.in to spring.beans;
    opens com.example.dlSpring.util to spring.core;

    exports com.example.dlSpring;
    exports com.example.dlSpring.model;
    exports com.example.dlSpring.controller;
    exports com.example.dlSpring.config;
    exports com.example.dlSpring.service;
    exports com.example.dlSpring.repository;
    exports com.example.dlSpring.dto;
    exports com.example.dlSpring.in;
    exports com.example.dlSpring.util;
    exports com.example.dlSpring.exception;
}