package classes;

import javax.swing.JButton;

/********************************
 * @author Sebastian Urwan
 * Telekomunikacja
 * Politechnika Gdańska
 *******************************/

public class MainFrame extends javax.swing.JFrame {
   
    public MainFrame() {
        initComponents();
        
        easyButton.setVisible(false);
        normalButton.setVisible(false);
        hardButton.setVisible(false);
        backButton.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        startButton = new javax.swing.JButton();
        nickButton = new javax.swing.JButton();
        highScoreButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        easyButton = new javax.swing.JButton();
        normalButton = new javax.swing.JButton();
        hardButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        menuBackground = new javax.swing.JLabel();
        ground = new javax.swing.JLabel();
        gameBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);

        jPanel1.setPreferredSize(new java.awt.Dimension(640, 480));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        startButton.setForeground(new java.awt.Color(245, 245, 245));
        startButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/btn.png"))); // NOI18N
        startButton.setText("start");
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        startButton.setFocusable(false);
        startButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        startButton.setMaximumSize(new java.awt.Dimension(150, 30));
        startButton.setMinimumSize(new java.awt.Dimension(150, 30));
        startButton.setPreferredSize(new java.awt.Dimension(150, 30));
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                startButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButtonMouseExited(evt);
            }
        });
        jPanel1.add(startButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, -1, -1));

        nickButton.setForeground(new java.awt.Color(245, 245, 245));
        nickButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/btn.png"))); // NOI18N
        nickButton.setText("player1");
        nickButton.setBorderPainted(false);
        nickButton.setContentAreaFilled(false);
        nickButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nickButton.setFocusable(false);
        nickButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nickButton.setMaximumSize(new java.awt.Dimension(150, 30));
        nickButton.setMinimumSize(new java.awt.Dimension(150, 30));
        nickButton.setPreferredSize(new java.awt.Dimension(150, 30));
        nickButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nickButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nickButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                nickButtonMouseExited(evt);
            }
        });
        jPanel1.add(nickButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, -1));

        highScoreButton.setForeground(new java.awt.Color(245, 245, 245));
        highScoreButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/btn.png"))); // NOI18N
        highScoreButton.setText("scores");
        highScoreButton.setBorderPainted(false);
        highScoreButton.setContentAreaFilled(false);
        highScoreButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        highScoreButton.setFocusable(false);
        highScoreButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        highScoreButton.setMaximumSize(new java.awt.Dimension(150, 30));
        highScoreButton.setMinimumSize(new java.awt.Dimension(150, 30));
        highScoreButton.setPreferredSize(new java.awt.Dimension(150, 30));
        highScoreButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                highScoreButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                highScoreButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                highScoreButtonMouseExited(evt);
            }
        });
        jPanel1.add(highScoreButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, -1, -1));

        exitButton.setForeground(new java.awt.Color(245, 245, 245));
        exitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/btn.png"))); // NOI18N
        exitButton.setText("exit");
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitButton.setFocusable(false);
        exitButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        exitButton.setMaximumSize(new java.awt.Dimension(150, 30));
        exitButton.setMinimumSize(new java.awt.Dimension(150, 30));
        exitButton.setPreferredSize(new java.awt.Dimension(150, 30));
        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitButtonMouseExited(evt);
            }
        });
        jPanel1.add(exitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, -1, -1));

        easyButton.setForeground(new java.awt.Color(245, 245, 245));
        easyButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/btn.png"))); // NOI18N
        easyButton.setText("easy");
        easyButton.setBorderPainted(false);
        easyButton.setContentAreaFilled(false);
        easyButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        easyButton.setFocusable(false);
        easyButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        easyButton.setMaximumSize(new java.awt.Dimension(150, 30));
        easyButton.setMinimumSize(new java.awt.Dimension(150, 30));
        easyButton.setPreferredSize(new java.awt.Dimension(150, 30));
        easyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                easyButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                easyButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                easyButtonMouseExited(evt);
            }
        });
        jPanel1.add(easyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, -1, -1));

        normalButton.setForeground(new java.awt.Color(245, 245, 245));
        normalButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/btn.png"))); // NOI18N
        normalButton.setText("normal");
        normalButton.setBorderPainted(false);
        normalButton.setContentAreaFilled(false);
        normalButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        normalButton.setFocusable(false);
        normalButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        normalButton.setMaximumSize(new java.awt.Dimension(150, 30));
        normalButton.setMinimumSize(new java.awt.Dimension(150, 30));
        normalButton.setPreferredSize(new java.awt.Dimension(150, 30));
        normalButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                normalButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                normalButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                normalButtonMouseExited(evt);
            }
        });
        jPanel1.add(normalButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, -1));

        hardButton.setForeground(new java.awt.Color(245, 245, 245));
        hardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/btn.png"))); // NOI18N
        hardButton.setText("hard");
        hardButton.setBorderPainted(false);
        hardButton.setContentAreaFilled(false);
        hardButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hardButton.setFocusable(false);
        hardButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        hardButton.setMaximumSize(new java.awt.Dimension(150, 30));
        hardButton.setMinimumSize(new java.awt.Dimension(150, 30));
        hardButton.setPreferredSize(new java.awt.Dimension(150, 30));
        hardButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hardButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hardButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hardButtonMouseExited(evt);
            }
        });
        jPanel1.add(hardButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, -1, -1));

        backButton.setForeground(new java.awt.Color(245, 245, 245));
        backButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/btn.png"))); // NOI18N
        backButton.setText("back");
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backButton.setFocusable(false);
        backButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        backButton.setMaximumSize(new java.awt.Dimension(150, 30));
        backButton.setMinimumSize(new java.awt.Dimension(150, 30));
        backButton.setPreferredSize(new java.awt.Dimension(150, 30));
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButtonMouseExited(evt);
            }
        });
        jPanel1.add(backButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, -1, -1));

        menuBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/CRR_logo.png"))); // NOI18N
        jPanel1.add(menuBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 480));

        ground.setBackground(new java.awt.Color(0, 0, 0));
        ground.setOpaque(true);
        jPanel1.add(ground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 640, 50));

        gameBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/bg.png"))); // NOI18N
        jPanel1.add(gameBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 480));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startButtonMouseClicked
        startButton.setVisible(false);
        nickButton.setVisible(false);
        highScoreButton.setVisible(false);
        exitButton.setVisible(false);
        easyButton.setVisible(true);
        normalButton.setVisible(true);
        hardButton.setVisible(true);
        backButton.setVisible(true);
    }//GEN-LAST:event_startButtonMouseClicked

    private void startButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startButtonMouseEntered
        onMouseEnter(startButton);
    }//GEN-LAST:event_startButtonMouseEntered

    private void startButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startButtonMouseExited
        onMouseExit(startButton);
    }//GEN-LAST:event_startButtonMouseExited

    private void nickButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nickButtonMouseClicked
        
    }//GEN-LAST:event_nickButtonMouseClicked

    private void nickButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nickButtonMouseEntered
        onMouseEnter(nickButton);
    }//GEN-LAST:event_nickButtonMouseEntered

    private void nickButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nickButtonMouseExited
        onMouseExit(nickButton);
    }//GEN-LAST:event_nickButtonMouseExited

    private void highScoreButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_highScoreButtonMouseClicked
        
    }//GEN-LAST:event_highScoreButtonMouseClicked

    private void highScoreButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_highScoreButtonMouseEntered
        onMouseEnter(highScoreButton);
    }//GEN-LAST:event_highScoreButtonMouseEntered

    private void highScoreButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_highScoreButtonMouseExited
        onMouseExit(highScoreButton);
    }//GEN-LAST:event_highScoreButtonMouseExited

    private void exitButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseClicked
        System.exit(0);
    }//GEN-LAST:event_exitButtonMouseClicked

    private void exitButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseEntered
        onMouseEnter(exitButton);
    }//GEN-LAST:event_exitButtonMouseEntered

    private void exitButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseExited
        onMouseExit(exitButton);
    }//GEN-LAST:event_exitButtonMouseExited

    private void easyButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_easyButtonMouseClicked
       
    }//GEN-LAST:event_easyButtonMouseClicked

    private void easyButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_easyButtonMouseEntered
        onMouseEnter(easyButton);
    }//GEN-LAST:event_easyButtonMouseEntered

    private void easyButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_easyButtonMouseExited
        onMouseExit(easyButton);
    }//GEN-LAST:event_easyButtonMouseExited

    private void normalButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_normalButtonMouseClicked
        
    }//GEN-LAST:event_normalButtonMouseClicked

    private void normalButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_normalButtonMouseEntered
        onMouseEnter(normalButton);
    }//GEN-LAST:event_normalButtonMouseEntered

    private void normalButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_normalButtonMouseExited
        onMouseExit(normalButton);
    }//GEN-LAST:event_normalButtonMouseExited

    private void hardButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hardButtonMouseClicked
       
    }//GEN-LAST:event_hardButtonMouseClicked

    private void hardButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hardButtonMouseEntered
        onMouseEnter(hardButton);
    }//GEN-LAST:event_hardButtonMouseEntered

    private void hardButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hardButtonMouseExited
        onMouseExit(hardButton);
    }//GEN-LAST:event_hardButtonMouseExited

    private void backButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backButtonMouseClicked
        startButton.setVisible(true);
        nickButton.setVisible(true);
        highScoreButton.setVisible(true);
        exitButton.setVisible(true);
        easyButton.setVisible(false);
        normalButton.setVisible(false);
        hardButton.setVisible(false);
        backButton.setVisible(false);
    }//GEN-LAST:event_backButtonMouseClicked

    private void backButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backButtonMouseEntered
        onMouseEnter(backButton);
    }//GEN-LAST:event_backButtonMouseEntered

    private void backButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backButtonMouseExited
        onMouseExit(backButton);
    }//GEN-LAST:event_backButtonMouseExited

    private void onMouseEnter(JButton btn){
        btn.setForeground(new java.awt.Color(255, 143, 159));
        btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/btn2.png")));
    }

    private void onMouseExit(JButton btn){
        btn.setForeground(new java.awt.Color(245, 245, 245));
        btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/btn.png")));
    }

    public static void main(String args[]) {

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton backButton;
    public static javax.swing.JButton easyButton;
    public static javax.swing.JButton exitButton;
    private javax.swing.JLabel gameBackground;
    private javax.swing.JLabel ground;
    public static javax.swing.JButton hardButton;
    public static javax.swing.JButton highScoreButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel menuBackground;
    public static javax.swing.JButton nickButton;
    public static javax.swing.JButton normalButton;
    public static javax.swing.JButton startButton;
    // End of variables declaration//GEN-END:variables
}
