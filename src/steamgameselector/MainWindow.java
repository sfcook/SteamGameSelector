/*
 * The MIT License
 *
 * Copyright 2014 sfcook.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package steamgameselector;

import javax.swing.JOptionPane;

/**
 *
 * @author sfcook
 */
public class MainWindow extends javax.swing.JFrame {
    
    private SteamGameSelector gameSelecter;
    
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitPane = new javax.swing.JSplitPane();
        panelLeft = new javax.swing.JPanel();
        scrLstGames = new javax.swing.JScrollPane();
        lstGames = new javax.swing.JList();
        panelRight = new javax.swing.JPanel();
        btnAddGame = new javax.swing.JButton();
        btnAddAccount = new javax.swing.JButton();
        scrLstAccounts = new javax.swing.JScrollPane();
        lstAccounts = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(500, 600));

        splitPane.setPreferredSize(new java.awt.Dimension(500, 600));

        panelLeft.setPreferredSize(new java.awt.Dimension(250, 600));

        scrLstGames.setPreferredSize(new java.awt.Dimension(150, 600));

        scrLstGames.setViewportView(lstGames);

        javax.swing.GroupLayout panelLeftLayout = new javax.swing.GroupLayout(panelLeft);
        panelLeft.setLayout(panelLeftLayout);
        panelLeftLayout.setHorizontalGroup(
            panelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrLstGames, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        panelLeftLayout.setVerticalGroup(
            panelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrLstGames, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
        );

        splitPane.setLeftComponent(panelLeft);

        btnAddGame.setText("Add Game");
        btnAddGame.setMaximumSize(new java.awt.Dimension(100, 23));
        btnAddGame.setMinimumSize(new java.awt.Dimension(100, 23));
        btnAddGame.setPreferredSize(new java.awt.Dimension(100, 23));
        btnAddGame.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddGameMouseClicked(evt);
            }
        });

        btnAddAccount.setText("Add Account");
        btnAddAccount.setMaximumSize(new java.awt.Dimension(100, 23));
        btnAddAccount.setMinimumSize(new java.awt.Dimension(100, 23));
        btnAddAccount.setPreferredSize(new java.awt.Dimension(100, 23));
        btnAddAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddAccountMouseClicked(evt);
            }
        });

        scrLstAccounts.setPreferredSize(new java.awt.Dimension(150, 130));

        scrLstAccounts.setViewportView(lstAccounts);

        javax.swing.GroupLayout panelRightLayout = new javax.swing.GroupLayout(panelRight);
        panelRight.setLayout(panelRightLayout);
        panelRightLayout.setHorizontalGroup(
            panelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrLstAccounts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnAddGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnAddAccount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelRightLayout.setVerticalGroup(
            panelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRightLayout.createSequentialGroup()
                .addComponent(scrLstAccounts, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddGame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        splitPane.setRightComponent(panelRight);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddGameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddGameMouseClicked
        AddGameWindow addPanel=new AddGameWindow(); //builds contents of a game obj
        JOptionPane.showConfirmDialog(this,addPanel,"Add Game",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        //todo: retrive result and do things
    }//GEN-LAST:event_btnAddGameMouseClicked

    private void btnAddAccountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddAccountMouseClicked
        String url = JOptionPane.showInputDialog(this,"Enter public url","Add Account",JOptionPane.PLAIN_MESSAGE);
        
        if(url==null)
            return;
        //validate using SteamUtils
        Account account=SteamUtils.getAccount(url);
        if(account.games.isEmpty())
        {
            AddAccountWindow addPanel=new AddAccountWindow();
            int result=JOptionPane.showConfirmDialog(this,addPanel,"Add Account",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
            if(result==JOptionPane.OK_OPTION)
            {
                account=addPanel.getAccount();
            }
            else
                return;
        }
        
        //TODO: do things with account obj
    }//GEN-LAST:event_btnAddAccountMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddAccount;
    private javax.swing.JButton btnAddGame;
    private javax.swing.JList lstAccounts;
    private javax.swing.JList lstGames;
    private javax.swing.JPanel panelLeft;
    private javax.swing.JPanel panelRight;
    private javax.swing.JScrollPane scrLstAccounts;
    private javax.swing.JScrollPane scrLstGames;
    private javax.swing.JSplitPane splitPane;
    // End of variables declaration//GEN-END:variables
}
