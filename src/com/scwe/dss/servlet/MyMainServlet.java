package com.scwe.dss.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jl.foundation.util.StringHelper;
import com.scwe.dss.pagebean.MyMainPageBean;
import com.scwe.dss.pagebean.IntelligentMonitoring.IMMainPageBean;
import com.scwe.dss.pagebean.designevaluation.DesignEvaluationPageBean;
import com.scwe.dss.pagebean.designmanagement.DesignEditPageBean;
import com.scwe.dss.pagebean.designmanagement.DesignEditSubmitPageBean;
import com.scwe.dss.pagebean.designmanagement.DesignManagementPageBean;
import com.scwe.dss.pagebean.designmanagement.DesignNewPageBean;
import com.scwe.dss.pagebean.designmanagement.DesignNewSubmitPageBean;
import com.scwe.dss.pagebean.designmanagement.DisplayDesignPageBean;
import com.scwe.dss.pagebean.designmanagement.DisplayDesignSubmitPageBean;
import com.scwe.dss.pagebean.designmanagement.ProjectDeleteSubmitPageBean;
import com.scwe.dss.pagebean.designmanagement.ProjectDeleteConfirmationPageBean;
import com.scwe.dss.pagebean.designmanagement.ProjectEditPageBean;
import com.scwe.dss.pagebean.designmanagement.ProjectEditSubmitPageBean;
import com.scwe.dss.pagebean.designmanagement.ProjectManagementPageBean;
import com.scwe.dss.pagebean.designmanagement.AllManagementPageBean;
import com.scwe.dss.pagebean.designmanagement.DesignDeleteConfirmationPageBean;
import com.scwe.dss.pagebean.designmanagement.DesignDeleteSubmitPageBean;
import com.scwe.dss.pagebean.designmanagement.ProjectNewPageBean;
import com.scwe.dss.pagebean.designmanagement.ProjectNewSubmitPageBean;
import com.scwe.dss.pagebean.designmanagement.RainManagementPageBean;
import com.scwe.dss.pagebean.designmanagement.ResultManagementPageBean;
import com.scwe.dss.pagebean.designmanagement.UploadFilePageBean;
import com.scwe.dss.pagebean.designmanagement.UploadFileSubmitPageBean;
import com.scwe.dss.util.AjaxHelper;
import com.scwe.dss.util.FileHelper;
 
/**
 * Servlet implementation class MyFirstServlet
 */

public class MyMainServlet extends iWorkerServlet {
	private static final long serialVersionUID = -7438491568367393531L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public MyMainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
    }
	 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  request.setCharacterEncoding("UTF-8"); 
	  String nextPageName = request.getParameter("Name");
	  String currentpageBean = request.getParameter("CurrentPageBean");
	  String tId = request.getParameter("SensorId");
	  if ("Ajax".equals(nextPageName)){
		AjaxHelper myAjaxHelper = new AjaxHelper();
		//myAjaxHelper.processRequest(request, response, myProperties, currentpageBean, getServletContext().getRealPath("/WEB-INF"));
		myAjaxHelper.processRequest(request, response, myProperties, currentpageBean);
		myAjaxHelper = null;
	  } else {

		if (StringHelper.isEmpty(nextPageName)){
		  request.getRequestDispatcher(getProperty("DEFAULT_PAGE")).forward(request, response);		  
		} else {
		  nextPageName = nextPageName + "Bean";
		  if (MyMainPageBean.PageBeanName.endsWith(nextPageName)){	
		    navigateTo(request, response, currentpageBean, MyMainPageBean.PageBeanName);
		  } else if (AllManagementPageBean.PageBeanName.endsWith(nextPageName)){	
			navigateTo(request, response, currentpageBean, AllManagementPageBean.PageBeanName);			  
		  } else if (DesignManagementPageBean.PageBeanName.endsWith(nextPageName)){	
			navigateTo(request, response, currentpageBean, DesignManagementPageBean.PageBeanName);			  
		  } else if (ProjectManagementPageBean.PageBeanName.endsWith(nextPageName)){	
			navigateTo(request, response, currentpageBean, ProjectManagementPageBean.PageBeanName);			  
		  } else if (ProjectNewPageBean.PageBeanName.endsWith(nextPageName)){	
			navigateTo(request, response, currentpageBean, ProjectNewPageBean.PageBeanName);			  
		  } else if (ProjectNewSubmitPageBean.PageBeanName.endsWith(nextPageName)){	
			navigateTo(request, response, currentpageBean, ProjectNewSubmitPageBean.PageBeanName);			  
		  } else if (ProjectEditSubmitPageBean.PageBeanName.endsWith(nextPageName)){	
			navigateTo(request, response, currentpageBean, ProjectEditSubmitPageBean.PageBeanName);			  
		  } else if (ProjectEditPageBean.PageBeanName.endsWith(nextPageName)){	
			navigateTo(request, response, currentpageBean, ProjectEditPageBean.PageBeanName);			  
		  } else if (ProjectDeleteConfirmationPageBean.PageBeanName.endsWith(nextPageName)){	
			  navigateTo(request, response, currentpageBean, ProjectDeleteConfirmationPageBean.PageBeanName);				
		  } else if (ProjectDeleteSubmitPageBean.PageBeanName.endsWith(nextPageName)){	
			  navigateTo(request, response, currentpageBean, ProjectDeleteSubmitPageBean.PageBeanName);				
		  } else if (DesignNewPageBean.PageBeanName.endsWith(nextPageName)){	
			navigateTo(request, response, currentpageBean, DesignNewPageBean.PageBeanName);			  
		  } else if (DesignNewSubmitPageBean.PageBeanName.endsWith(nextPageName)){	
			navigateTo(request, response, currentpageBean, DesignNewSubmitPageBean.PageBeanName);			  
		  } else if (DesignEditPageBean.PageBeanName.endsWith(nextPageName)){	
			  navigateTo(request, response, currentpageBean, DesignEditPageBean.PageBeanName);				
		  } else if (DesignEditSubmitPageBean.PageBeanName.endsWith(nextPageName)){	
			navigateTo(request, response, currentpageBean, DesignEditSubmitPageBean.PageBeanName);		
		  } else if (DesignDeleteConfirmationPageBean.PageBeanName.endsWith(nextPageName)){	
			navigateTo(request, response, currentpageBean, DesignDeleteConfirmationPageBean.PageBeanName);	
		  } else if (IMMainPageBean.PageBeanName.endsWith(nextPageName)){	
			  navigateTo(request, response, currentpageBean, IMMainPageBean.PageBeanName);				
		  } else if (DesignDeleteSubmitPageBean.PageBeanName.endsWith(nextPageName)){	
			  navigateTo(request, response, currentpageBean, DesignDeleteSubmitPageBean.PageBeanName);				
		  } else if (RainManagementPageBean.PageBeanName.endsWith(nextPageName)){	
			  navigateTo(request, response, currentpageBean, RainManagementPageBean.PageBeanName);	
		  } else if (ResultManagementPageBean.PageBeanName.endsWith(nextPageName)){	
			  navigateTo(request, response, currentpageBean, ResultManagementPageBean.PageBeanName);	
		  } else if (DisplayDesignPageBean.PageBeanName.endsWith(nextPageName)){	
			  navigateTo(request, response, currentpageBean, DisplayDesignPageBean.PageBeanName);	
		  } else if (DisplayDesignSubmitPageBean.PageBeanName.endsWith(nextPageName)){	
			  navigateTo(request, response, currentpageBean, DisplayDesignSubmitPageBean.PageBeanName);				
		  } else if (DesignEvaluationPageBean.PageBeanName.endsWith(nextPageName)){	
			navigateTo(request, response, currentpageBean, DesignEvaluationPageBean.PageBeanName);			  
		  } else if (UploadFilePageBean.PageBeanName.endsWith(nextPageName)){	
			navigateTo(request, response, currentpageBean, UploadFilePageBean.PageBeanName);			  
		  } else if (UploadFileSubmitPageBean.PageBeanName.endsWith(nextPageName)){	
		    if (StringHelper.isEmpty(currentpageBean))
			  currentpageBean = "com.scwe.dss.pagebean.designmanagement.UploadFilePageBean";
			navigateTo(request, response, currentpageBean, UploadFileSubmitPageBean.PageBeanName);			  
		  }  
		}
		
	  }
		
	}

    @Override
    public String getServletInfo() {
        return "MyFirstServlet";
    }
	
}
