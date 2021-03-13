package com.ujwal.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ujwal.products.HibernateUtil;
import com.ujwal.products.Product;
import com.ujwal.products.ProdSession;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import javax.servlet.annotation.WebServlet;





/**
 * Servlet implementation class PetsServlet
 */
//@Webservlet("/products");
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
        try {
               SessionFactory factory = HibernateUtil.getSessionFactory();
               
               Session session = factory.openSession();
               // using HQL
               List<Product> list = session.createQuery("from Product", Product.class).list();
               
               session.close();
               
                PrintWriter out = response.getWriter();
                out.println("<html><body>");
                out.println("<b>Product Listing</b><br>");
                for(Product p: list) {
                        out.println( "Name: " + p.getName() +
                                        ", Price: " + String.valueOf(p.getPrice()) + ", Color: " + p.getColor().toString() + "<br>");
                }
                
            out.println("</body></html>");
            
            
        } catch (Exception ex) {
                throw ex;
        }
        
        PrintWriter out = response.getWriter();
        out.println("<html><body style='background-color:#EFF5FB;'>");
		out.println("<html><body style='text-align:center'></br>");
		out.println("<p style='text-align:center;'><a href='index.jsp'>Back to Add Pet Page</a></p><br/>");
        out.println("</body></html>");

		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String color = request.getParameter("color");
		String price = request.getParameter("price");
		double zeroCheck = Double.parseDouble(price);
		//HttpSession session = request.getSession(true);
		PrintWriter out = response.getWriter();
		/*try {
			SessionFactory factory = HibernateUtil.getSessionFactory();
	        
	        Session session = factory.openSession();
	        session.beginTransaction();
	        
	        Product ep = new Product();
	        ep.setName(name);
	        ep.setPrice(price);
	        ep.setColor(color);
	        
	        //session.save(ep);
	        session.getTransaction().commit();
	        
	        // using HQL
	        List<Product> list = session.createQuery("from Product", Product.class).list();
	        
	        session.close();
	        
	        PrintWriter out = response.getWriter();
	        out.println("<html><body>");
	        out.println("<b>Adding Pet</b> " + request.getParameter("name") + "<br>");
	        for (Product p : list) {
	        	out.println("ID: " + String.valueOf(p.getID()) + ", Name: " + p.getName() + ", Price: "
						+ String.valueOf(p.getPrice()) + ", Color: " + p.getColor().toString() + "<br>");
	        }
	        out.println("<a href='index.jsp'>Return to Main</a><br>");
	        out.println("</body></html>");
        
        
        
        //TODO: Take all parameters from post, and use hibernate to insert new pet.
        // Then you need to print out some confirmation as to the the success/failure.
        // You also need to validate your input and not allow missing / NULL data.

		//doGet(request, response);
		}
		catch (Exception ex) {
			throw ex;
		}*/
		
		try {	
			ProdSession prodDAO = new ProdSession(); //data access object
			if(zeroCheck <= 0) {	
				response.setContentType("text/html");
				out.println("<html><body style='background-color:#EFF5FB;'>");
				out.println("<h2 style='text-align:center'>No zero or negative values allowed...</h2><br/>");
				out.println("<p style='text-align:center';><a href ='addProd.jsp'>click here to try again...</a></p>");
				out.println("</body></html>");
			} else {
			prodDAO.addProdDetails(name, color, price);
			response.sendRedirect("Success");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}