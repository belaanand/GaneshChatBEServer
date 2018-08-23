package com.perfectganesh.ganeshchatbeserver;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

@SuppressWarnings("serial")
public class GaneshSendServlet extends HttpServlet {
     
    //private static final Logger logger = Logger.getLogger(SendServlet.class.getCanonicalName());
 
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = req.getParameter(Constants.MSG);
        String from = req.getParameter(Constants.FROM);
        String to = req.getParameter(Constants.TO);
         
        GaneshContact contact = null;
        EntityManager em = GaneshEMFService.get().createEntityManager();
        try {
            contact = GaneshContact.find(to, em);
            if (contact == null) return;
        } finally {
            em.close();
        }
         
        String regId = contact.getRegId();
        Sender sender = new Sender(Constants.API_KEY);
        Message message = new Message.Builder()
//          .delayWhileIdle(true)
            .addData(Constants.TO, to).addData(Constants.FROM, from).addData(Constants.MSG, msg)
            .build();
         
        try {
            Result result = sender.send(message, regId, 5);
/*          List<String> regIds = new ArrayList<String>();
            regIds.add(regId);
            MulticastResult result = sender.send(message, regIds, 5);*/
             
            //logger.log(Level.WARNING, "Result: " + result.toString());
        } catch (IOException e) {
            //logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
