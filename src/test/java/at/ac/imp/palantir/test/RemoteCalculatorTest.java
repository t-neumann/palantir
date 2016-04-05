///*
// * JBoss, Home of Professional Open Source
// * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
// * contributors by the @authors tag. See the copyright.txt in the
// * distribution for a full listing of individual contributors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// * http://www.apache.org/licenses/LICENSE-2.0
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package at.ac.imp.palantir.test;
//
//import static org.junit.Assert.assertEquals;
//
//import javax.ejb.EJB;
//
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.Archive;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.EmptyAsset;
//import org.jboss.shrinkwrap.api.spec.WebArchive;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import at.ac.imp.palantir.controller.GeneBean;
//import at.ac.imp.palantir.controller.GeneHandler;
//import at.ac.imp.palantir.exceptions.DatabaseException;
//import at.ac.imp.palantir.model.Alignment;
//import at.ac.imp.palantir.model.Datapoint;
//import at.ac.imp.palantir.model.ExpressionValue;
//import at.ac.imp.palantir.model.Gene;
//import at.ac.imp.palantir.model.Reference;
//import at.ac.imp.palantir.model.Result;
//import at.ac.imp.palantir.model.Sample;
//
//@RunWith(Arquillian.class)
//public class RemoteCalculatorTest {
//    @Deployment
//    public static Archive<?> createTestArchive() {
//        return ShrinkWrap.create(WebArchive.class, "test.war")
//                .addClasses(GeneHandler.class, GeneBean.class, Gene.class, DatabaseException.class, Reference.class, Datapoint.class, Result.class, Alignment.class, Sample.class, ExpressionValue.class)
//                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
//        		.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
//                // Deploy our test datasource
//                //.addAsWebInfResource("test-ds.xml");
//    }
//    
//    @EJB
//    GeneHandler genehandler;
//    
//    @Test
//    public void testHandler() throws Exception {
//    	
//    	String symbol = "BRCA1";
//    	
//    	Gene gene = genehandler.getGeneByName(symbol);
// 
//    	System.out.println(gene);
//    	assertEquals(gene.getGeneSymbol(),symbol);
//    }
//}
