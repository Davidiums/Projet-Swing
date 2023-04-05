package StaticMethode;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public abstract class MethodeJtable {

    public static void remplirJTable(JTable table, DefaultTableModel model, ResultSet rs) {
        // Vider le contenu du model
        model.setRowCount(0);

        // Récupérer les métadonnées du ResultSet
        ResultSetMetaData metaData;
        try {
            metaData = rs.getMetaData();

            // Récupérer le nombre de colonnes
            int columnCount = metaData.getColumnCount();

            // Parcourir le ResultSet et ajouter chaque ligne au model
            while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = rs.getObject(i+1);
                }
                model.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Affecter le model à la JTable
        table.setModel(model);
    }

    public static void initJtable(JTable jt){
        jt.setRowSelectionAllowed(true);
        jt.setColumnSelectionAllowed(false);
        jt.setDefaultEditor(Object.class, null);
        jt.setSelectionBackground(Color.YELLOW);
    }

    public static void stopSelection(JTable jt, JPanel p){
        p.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Vérifier si l'utilisateur a cliqué en dehors du JTable
                if (!jt.contains(e.getPoint())) {
                    // Effacer la sélection du JTable
                    jt.clearSelection();
                }
            }
        });
    }

}
