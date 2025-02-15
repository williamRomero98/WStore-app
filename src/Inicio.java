import java.awt.BorderLayout;
import java.lang.Object.*;
import java.awt.EventQueue;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;
import net.proteanit.sql.DbUtils;
import sun.misc.GC;

import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.sqlite.SQLiteConnection;

import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.List;
import java.awt.ScrollPane;
import java.awt.Choice;
import java.sql.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.beans.VetoableChangeListener;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Font;
import java.awt.Color;
public class Inicio extends JFrame {

	private JPanel contentPane;
	private JTable tableInventario;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inicio frame = new Inicio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	// Variables propias de SQL		
	
	
	
	private JComboBox comboBoxInventario;
	private JTextField textNID;
	private JTextField textPhone;
	private JTextField textDir;
	private JTextField TextNombre;
	//Otras variables auxiliares
	private String oldNid;
	private String nbr_cl_ti;
	private int pcom_cl_ti;
	private int pvnt_cl_ti;
	private int cnt_cl_ti;
	
	//Variables de clase
	Inventario inventerio = new Inventario();
	
	//Variables de tabla
	Object[] header = new Object[]{"ID","Nombre","Precio_Compra","Precio_Venta","Cantidad"};
	DefaultTableModel model = new DefaultTableModel(header, 0);
	
	//M�todo para actualizar tabla inventario
	private void upDateTable()
	{
		model.setRowCount(0);
		ArrayList<Producto> rs = null;
		rs = inventerio.mostrarProductos();
		for(Producto prod : rs)
		{
			model.addRow(new Object[]{prod.getID(),prod.getNombre(),prod.getPrecio_Compra(),prod.getPrecio_Venta(),prod.getStock()});	
			tableInventario.setModel(model);
		}
	}
	
	//M�todo para actualizar tabla inventario segun un nombre
	private void searchNameinTable(String name)
	{
		model.setRowCount(0);
		ArrayList<Producto> rs = null;
		rs = inventerio.mostrarProductos();
		for(Producto prod : rs)
		{
			if(prod.getNombre().contentEquals(name))
			{
				model.addRow(new Object[]{prod.getID(),prod.getNombre(),prod.getPrecio_Compra(),prod.getPrecio_Venta(),prod.getStock()});	
				tableInventario.setModel(model);
			}
		}
	}
	
	
	//M�todo para actualizar popUp menu
	private void upDatePopMenu()
	{
		comboBoxInventario.removeAllItems();
		comboBoxInventario.addItem("");
		ArrayList<Producto> rs = null;
		rs = inventerio.mostrarProductos();
		for(Producto prod : rs)
		{
			comboBoxInventario.addItem(prod.getNombre());
		}
	}
	 
	
	private void updateDatosNegocio()
	{
		Connection conne=sqliteconnection.dbconnector();
		PreparedStatement pst = null;	
		ResultSet rs = null;
		String comn1 = "select * from DatosNegocio";		
		try {			
			pst = conne.prepareStatement(comn1);
			rs = pst.executeQuery();		
			if(rs!=null)
			{
				TextNombre.setText(rs.getString("Nombre"));
				textDir.setText(rs.getString("Direccion"));
				textNID.setText(rs.getString("Nid"));
				textPhone.setText(rs.getString("Telefono"));
				oldNid = rs.getString("Nid");
			}
			else
			{
				
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Error al llenar la tabla - 4: "+e);
		}			
		
	}
	
	public String getNID()
	{
		return oldNid;
	}
	
	
	public void setNbr_byclick(String nn)
	{
		nbr_cl_ti = nn;	
	}
	
	
	public void setPcom_byclick(int nn)
	{
		pcom_cl_ti = nn;	
	}
	
	
	public void setPvent_byclick(int nn)
	{
		pvnt_cl_ti = nn;	
	}
	
	public int getCnt_byclick()
	{
		return cnt_cl_ti;
	}
	public void setCnt_byclick(int nn)
	{
		cnt_cl_ti = nn;	
	}
	
	
	
	private void setNid(String st) {oldNid = st;}
	
	// Inicio del constructor	
	public Inicio() {
		setResizable(false);
		setTitle("Inicio");
		setForeground(Color.DARK_GRAY);
		setFont(new Font("Arial Narrow", Font.BOLD, 13));
		
		//Conexi�n a SQLite					
					
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 817, 344);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setForeground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblInventario = new JLabel("INVENTARIO");
		lblInventario.setFont(new Font("Sitka Text", Font.BOLD, 13));
		
		JLabel lblInventario_2 = new JLabel("DIRECCI\u00D3N");
		lblInventario_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblInventario_3 = new JLabel("NID");
		lblInventario_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblInventario_4 = new JLabel("TEL\u00C9FONO");
		lblInventario_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblRowSelected = new JLabel("----");
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String rwiD = lblRowSelected.getText();
				if(rwiD!="")
				{						
					UpdateProduct pgUp_Prod = new UpdateProduct(Integer.parseInt(rwiD));	
					pgUp_Prod.setVisible(true);							
				}
				else
				{
					
				}
				upDateTable();
				
			}
		});
		
		JButton btnEditData = new JButton("Editar");		
		btnEditData.addMouseListener(new MouseAdapter() {			
			@Override			
			public void mouseClicked(MouseEvent e) {				
				if(!btnEditData.getText().equals("Editar"))
				{
					Connection conne=sqliteconnection.dbconnector();
					PreparedStatement pst = null;						
					String Dtname = TextNombre.getText();
					String DtDir = textDir.getText();
					String Dtph = textPhone.getText();
					String Dtnid = textNID.getText();
					int rsi = 0;
					String comn1 = "update DatosNegocio set Nombre='"+Dtname+"',NID='"+Dtnid+"',Telefono='"+Dtph+"',Direccion='"+DtDir+"' where id='"+1+"';";	
					try {						
						pst = conne.prepareStatement(comn1);
						rsi = pst.executeUpdate();						
						setNid(textNID.getText());
						rsi=0;
						pst.close();
						JOptionPane.showMessageDialog(null,"Actualizado con �xito:");
					}catch (SQLException e1) {
						
					}					
					btnEditData.setText("Editar");
					TextNombre.setEditable(false);
					textDir.setEditable(false);
					textNID.setEditable(false);
					textPhone.setEditable(false);
				}
				else
				{
					String oldNid = textNID.getText();					
					btnEditData.setText("Guardar");
					TextNombre.setEditable(true);
					textDir.setEditable(true);
					textNID.setEditable(true);
					textPhone.setEditable(true);
				}
			}
		});
		
		
		JButton btnVentas = new JButton("Contabilidad");
		btnVentas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Historial historial = new Historial();
				historial.setVisible(true);
			}
		});
		
		JButton btnCompra_1 = new JButton("Compra");
		btnCompra_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				CreateCompra pagCompra = new CreateCompra();
				pagCompra.setVisible(true);
			}
		});
		
		JButton btnVenta = new JButton("Venta");
		btnVenta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				CreateVenta pagVnt = new CreateVenta();
				pagVnt.setVisible(true);
			}
		});
		
		JButton btnModificar_6 = new JButton("Clientes y Proveedores");
		btnModificar_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Terceros terceros = new Terceros();
				terceros.setVisible(true);
			}
		});
		
		JScrollPane scrollPaneCombo = new JScrollPane();
		
		tableInventario = new JTable();
		tableInventario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				JTable source = (JTable)evt.getSource();
	            int row = source.rowAtPoint( evt.getPoint() );
	            int column = source.columnAtPoint( evt.getPoint() );
	            lblRowSelected.setText(""+ source.getModel().getValueAt(row, 0) );
	            setNbr_byclick(""+ source.getModel().getValueAt(row, 1));
	            setPcom_byclick((int)source.getModel().getValueAt(row, 2));
	            setPvent_byclick((int)source.getModel().getValueAt(row, 3));
	            setCnt_byclick((int)source.getModel().getValueAt(row, 4));
			}
		});
		
		tableInventario.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "nombre", "precio_compra", "precio_venta", "Cantidad"
			}
		));
		tableInventario.getColumnModel().getColumn(0).setPreferredWidth(55);
		tableInventario.getColumnModel().getColumn(1).setPreferredWidth(92);
		tableInventario.getColumnModel().getColumn(2).setPreferredWidth(93);
		tableInventario.getColumnModel().getColumn(3).setPreferredWidth(88);
		tableInventario.getColumnModel().getColumn(4).setPreferredWidth(88);
		scrollPane.setViewportView(tableInventario);
		
		textNID = new JTextField();
		textNID.setEditable(false);
		textNID.setText("------");
		textNID.setColumns(10);
		
		textPhone = new JTextField();
		textPhone.setEditable(false);
		textPhone.setToolTipText("");
		textPhone.setText("------");
		textPhone.setColumns(10);
		
		textDir = new JTextField();
		textDir.setText("------");
		textDir.setEditable(false);
		textDir.setToolTipText("");
		textDir.setColumns(10);
		
		TextNombre = new JTextField();
		TextNombre.setBackground(Color.WHITE);
		TextNombre.setHorizontalAlignment(SwingConstants.CENTER);
		TextNombre.setForeground(Color.DARK_GRAY);
		TextNombre.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 12));
		TextNombre.setEditable(false);
		TextNombre.setText("Nombre_Negocio");
		TextNombre.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addComponent(lblInventario_4, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
											.addComponent(lblInventario_3, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
											.addGroup(gl_contentPane.createSequentialGroup()
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
													.addComponent(textNID, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
													.addComponent(textDir, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
													.addComponent(textPhone, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE))
												.addGap(7))
											.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(TextNombre, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
												.addGap(94))))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(btnModificar_6, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblInventario_2, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnEditData)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(44)
							.addComponent(btnCompra_1, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnVenta, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
							.addGap(39))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnVentas, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblInventario, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(scrollPaneCombo, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(10)
								.addComponent(lblRowSelected, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnModificar, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE))
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 435, GroupLayout.PREFERRED_SIZE)))
					.addGap(99))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblInventario)
							.addComponent(TextNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPaneCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnEditData)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblInventario_3)
								.addComponent(textNID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblInventario_4)
								.addComponent(textPhone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(3)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblInventario_2)
								.addComponent(textDir, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(24)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnVenta)
								.addComponent(btnCompra_1))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnModificar_6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnVentas)
							.addGap(40))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblRowSelected)
								.addComponent(btnModificar))))
					.addContainerGap())
		);
		
		comboBoxInventario = new JComboBox();	
		comboBoxInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBoxInventario.getSelectedItem() == "")
				{
					upDateTable();	
					comboBoxInventario.removeAllItems();
					upDatePopMenu();
				}
				else
				{
					searchNameinTable(""+comboBoxInventario.getSelectedItem());
					comboBoxInventario.removeAllItems();
					upDatePopMenu();
				}
				
			}
		});
		
		
		
	
		
		
		
		
		
	
				
		scrollPaneCombo.setViewportView(comboBoxInventario);
		
		contentPane.setLayout(gl_contentPane);		
		//upDateTable();
		//comboBoxInventario.removeAllItems();	
		updateDatosNegocio();
		upDatePopMenu();
				
		
			} // Fin del constructor
	
	
	
	public JComboBox getComboBoxInventario() {
		return comboBoxInventario;
	}
}
