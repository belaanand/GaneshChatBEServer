package com.perfectganesh.ganeshchatbeserver;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GaneshRegisterServlet extends HttpServlet {
     
    //private static final Logger logger = Logger.getLogger(RegisterServlet.class.getCanonicalName());
 
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter(Constants.FROM);
        String regId = req.getParameter(Constants.REG_ID);
         
        EntityManager em = GaneshEMFService.get().createEntityManager();
        try {
            GaneshContact contact = GaneshContact.find(email, em);
            if (contact == null) {
                contact = new GaneshContact(email, regId);
            } else {
                contact.setRegId(regId);
            }
            em.persist(contact);
            //logger.log(Level.WARNING, "Registered: " + contact.getId());
        } finally {
            em.close();
        }
    }
}

