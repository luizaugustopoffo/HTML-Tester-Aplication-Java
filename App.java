package Trabalho1;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextPane;

public class App extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private JTextField txtArquivo;
	private LeitorDeTags ldt = new LeitorDeTags();
	private static JTable tbTagsEFrequencia;
	private static JScrollPane scrollPaneTable = new JScrollPane();
	private static JScrollPane scrollPaneStatus = new JScrollPane();
	private static JTextPane txtStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App();
					frame.setVisible(true);
					scrollPaneTable.setBounds(tbTagsEFrequencia.getBounds());
					contentPane.add(scrollPaneTable);
					scrollPaneTable.setViewportView(tbTagsEFrequencia);
					scrollPaneStatus.setBounds(txtStatus.getBounds());
					contentPane.add(scrollPaneStatus);
					scrollPaneStatus.setViewportView(txtStatus);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public App() {
		setTitle("Avaliador De Arquivos HLTM");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 554, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		JLabel lbArquivo = new JLabel("Arquivo:");
		txtArquivo = new JTextField();
		txtArquivo.setColumns(10);
		JButton btnAnalisar = new JButton("Analisar");
		tbTagsEFrequencia = new JTable();
		tbTagsEFrequencia.setRowSelectionAllowed(false);
		txtStatus = new JTextPane();

		btnAnalisar.addActionListener((new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((DefaultTableModel) tbTagsEFrequencia.getModel()).setNumRows(0);
				tbTagsEFrequencia.updateUI();
				ldt.getListaFrequenciaTags().liberar();
				ldt.setStringErros("");
				try {
					ldt.leitorEInterpretadorDeTags(txtArquivo.getText());
					if (ldt.getStringErros().equals("")) {
						txtStatus.setText("O arquivo está bem formatado.");
						DefaultTableModel modelo = ((DefaultTableModel) tbTagsEFrequencia.getModel());
						ListaEncadeada<TagEFrequencia> lista = ldt.getListaFrequenciaTags();
						String[] linha = new String[2];
						for (int i = 0; i < lista.obterComprimento(); i++) {
							linha[0] = lista.obterNo(i).getInfo().getNome();
							linha[1] = lista.obterNo(i).getInfo().getFrequencia() + "";
							modelo.addRow(linha);
						}
					} else {
						txtStatus.setText(ldt.getStringErros());
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "ERRO!");
					e.printStackTrace();
				}
			}
		}));

		tbTagsEFrequencia.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Tag", "Numero de Ocorr\u00EAncias" }) {
					Class[] columnTypes = new Class[] { String.class, String.class };
				});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lbArquivo, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtArquivo, GroupLayout.PREFERRED_SIZE, 373, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAnalisar, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(tbTagsEFrequencia, GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
					.addGap(6))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(txtStatus, GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbArquivo)
						.addComponent(txtArquivo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAnalisar, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtStatus, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
					.addGap(9)
					.addComponent(tbTagsEFrequencia, GroupLayout.PREFERRED_SIZE, 247, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
