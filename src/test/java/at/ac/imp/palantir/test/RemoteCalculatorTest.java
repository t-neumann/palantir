/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package at.ac.imp.palantir.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.ac.imp.palantir.controller.GeneBean;
import at.ac.imp.palantir.controller.GeneHandler;
import at.ac.imp.palantir.entities.Gene;

@RunWith(Arquillian.class)
public class RemoteCalculatorTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(GeneHandler.class, GeneBean.class, Gene.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                // Deploy our test datasource
                .addAsWebInfResource("test-ds.xml");
    }
    
    @EJB
    GeneHandler genehandler;
    
    @Test
    public void testHandler() throws Exception {
    	
    	Gene gene = new Gene("test", 1, 2, "test2", false, 3);
    	//genehandler.addGene(gene);
    	
    	boolean found = false;
    	
    	//List<Gene> genes = genehandler.getGenes();
    	List<Gene> genes = new ArrayList<Gene>();
    	
    	for (Gene i : genes) {
    		if (i.equals(gene)) {
    			System.out.println(i);
    			found = true;
    		}
    	}    	
    	System.out.println(gene);
    	assertTrue(found);
    }

}
