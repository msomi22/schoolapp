/**
 * Copy Right 2018. Qubit Intelligent Solutions Ltd.
 *                . website: http://qubintel.co.ke
 *                . email:   info@qubintel.co.ke 
 *                
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.qubintel.school.server.servlet.reports.test2;

import java.util.ArrayList;
import java.util.List;

import ke.co.qubintel.school.server.bean.exam.YearlyMean;


public class TTTTTT {

	public TTTTTT() {

	}

	public static void main(String[] args) {


		List<YearlyMean> list = new ArrayList<>();
		YearlyMean obj = new YearlyMean();
		obj.setMeanOne(10.4);
		obj.setMeanTwo(11.6);
		obj.setMeanThree(13.6);
		obj.setTermOnePosition("1/3");
		obj.setTermTwoPosition("2/3");
		obj.setTermThreePosition("3/3"); 
		obj.setClassId("FORM 2"); 

		YearlyMean obj2 = new YearlyMean();
		obj2.setMeanOne(10.4);
		obj2.setMeanTwo(11.6);
		obj2.setMeanThree(13.6);
		obj2.setTermOnePosition("1/3");
		obj2.setTermTwoPosition("2/3");
		obj2.setTermThreePosition("3/3"); 
		obj2.setClassId("FORM 3"); 


		list.add(obj);
		list.add(obj2);

		PerformanceTable pt = new PerformanceTable();


		list.forEach(yearlyMean -> {


			if(yearlyMean.getClassId().equals("FORM 2")) {

				TermOneObj t1 = new TermOneObj();
				Forms forms_t1 = new Forms(); ///
				FormTwo formTwo_t1 = new FormTwo(); 
				MP mp_t1 = new MP();
				mp_t1.setMean(yearlyMean.getMeanOne()+""); 
				mp_t1.setPos(yearlyMean.getTermOnePosition()); 
				formTwo_t1.setMp(mp_t1);
				forms_t1.setFormTwo(formTwo_t1);
				t1.setForms(forms_t1); 


				TermTwoObj t2 = new TermTwoObj();
				Forms forms_t2 = new Forms();
				FormTwo formTwo_t2 = new FormTwo(); 
				MP mp_t2 = new MP();
				mp_t2.setMean(yearlyMean.getMeanTwo()+""); 
				mp_t2.setPos(yearlyMean.getTermTwoPosition()); 
				formTwo_t2.setMp(mp_t2);
				forms_t2.setFormTwo(formTwo_t2);
				t2.setForms(forms_t2); 
				
				
				TermThreeObj t3 = new TermThreeObj();
				Forms forms_t3 = new Forms();
				FormTwo formTwo_t3 = new FormTwo(); 
				MP mp_t3 = new MP();
				mp_t3.setMean(yearlyMean.getMeanThree()+""); 
				mp_t3.setPos(yearlyMean.getTermThreePosition()); 
				formTwo_t3.setMp(mp_t3);
				forms_t3.setFormTwo(formTwo_t3);
				t3.setForms(forms_t3); 

				pt.setTermOneObj(t1);
				pt.setTermTwoObj(t2);
				pt.setTermThreeObj(t3);


			}



		});
		
		
		
		
		
		
		
		
		System.out.println(pt); 
		//System.out.println(pt); 


	}

}
