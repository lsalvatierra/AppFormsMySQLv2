import adapter.ComboItem;
import adapter.CursoTableModel;
import dao.CursoDAO;
import model.Curso;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class frmMantCurso extends JDialog {
    private JPanel root;
    private JTextField txtcodcurso;
    private JTextField txtnomcurso;
    private JTextField txtcredcurso;
    private JTable tblcursos;
    private JButton btnregistrar;
    private JButton btnlimpiar;
    private JScrollPane spcursos;
    private JComboBox cbocurso;


    public static void main(String[] args) {
        JFrame frame = new JFrame("frmMantCurso");
        frame.setContentPane(new frmMantCurso().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public frmMantCurso() {
        listarCursos();


        btnregistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarCurso();
                limpiarControles();
            }
        });
        btnlimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //limpiarControles();
                Object item = cbocurso.getSelectedItem();
                String value = ((ComboItem)item).getValue();
                System.out.println(value);
            }
        });
    }

    public void registrarCurso(){
        String codcurso = txtcodcurso.getText();
        String nomcurso = txtnomcurso.getText();
        String credcurso = txtcredcurso.getText();
        if(codcurso.isEmpty() || nomcurso.isEmpty() || credcurso.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Ingrese todos los datos del curso",
                    "Error!!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Curso curso = new Curso(codcurso,
                nomcurso, Integer.parseInt(credcurso));
        if(new CursoDAO().registrarCurso(curso)){
            JOptionPane.showMessageDialog(this,
                    "El curso fue registrado correctamente.",
                    "Correcto!!",
                    JOptionPane.INFORMATION_MESSAGE);
            //cursoList.add(curso);
            limpiarTabla();
            listarCursos();
        }else{
            JOptionPane.showMessageDialog(this,
                    "Error al registrar en la BD",
                    "Error!!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiarControles(){
        txtcodcurso.setText("");
        txtnomcurso.setText("");
        txtcredcurso.setText("");
    }

    private void limpiarTabla(){
        CursoTableModel tb = (CursoTableModel) tblcursos.getModel();
        tb.removeRowAt();
    }

    public void listarCursos(){
        List<Curso> cursoList = new ArrayList<>();
        for(Curso objCurso : new CursoDAO().listarCursos()){
            cursoList.add(objCurso);
            cbocurso.addItem(new ComboItem(objCurso.getNomcurso(),
                    objCurso.getIdcurso()));
        }
        CursoTableModel cursoTableModel = new CursoTableModel(cursoList);
        tblcursos.setModel(cursoTableModel);
        cursoTableModel.fireTableDataChanged();

    }
}
