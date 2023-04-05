package StaticMethode;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

public abstract class MethodeTextField {
    public static void setMaxChar(JTextField textField, int maxChar) {
        textField.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
                if ((getLength() + str.length()) <= maxChar) {
                    super.insertString(offset, str, attr);
                }
            }
        });
    }

    public static void acceptOnlyNumbers(JTextField textField) {
        // Créer un DocumentFilter qui accepte uniquement les chiffres
        DocumentFilter filter = new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                // Vérifier si la chaîne d'entrée contient uniquement des chiffres
                if (string.matches("\\d+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                // Vérifier si la chaîne de remplacement contient uniquement des chiffres
                if (text.matches("\\d+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        };

        // Appliquer le DocumentFilter au JTextField
        textField.setDocument(new javax.swing.text.PlainDocument());
        ((javax.swing.text.AbstractDocument) textField.getDocument()).setDocumentFilter(filter);
    }
}
