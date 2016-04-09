package guiPacket;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import cards.Unit;

/**
 * GUI class that contains events for when a card is hovered over. The last card
 * hovered over will be shown in a panel.
 * 
 * @author emilsundberg, 13120dde
 *
 */
public class InfoPanelGUI extends JPanel {
	private BoardGuiController boardController;
	
	private JPanel cardContainer = new JPanel();
	private JPanel cardPanel1 = new JPanel();
	private JPanel cardPanel2 = new JPanel();
	private JPanel midEmpty = new JPanel();
	
	private JPanel twContainer = new JPanel();
	private JPanel twPanel1= new JPanel();
	private JPanel twPanel2= new JPanel();
	
	private static JEditorPane  editorPane = new JEditorPane();
	private static String pre = "<html><body style='font-size: 12px;'><ul>";
	private static StringBuilder stringBuilder = new StringBuilder();
	private static String font = "BankGothic Md BT";
	
	
	private Border b1 = BorderFactory.createEmptyBorder(0, 10, 0, 10);
	
	private ImageIcon background = new ImageIcon("files/pictures/infoPanelTexture.jpg");
	
//	private static JTextArea textArea = new JTextArea();
	private static JScrollPane scrollPane = new JScrollPane( editorPane );
	


	public InfoPanelGUI(BoardGuiController boardController) {
		this.boardController = boardController;
		boardController.addHoveredCardlListener(this);
		
		customizeCardPanel();
		customizeTWPanel();
		customizeEditorPane();
		
		//FOR DEBUGGING
//		showPanelBorders();
		midEmpty.setOpaque(false);
		
		this.setLayout(new GridLayout(3,1));
//		this.setOpaque(false);
		this.add(cardPanel2);
		this.add(midEmpty);
		this.add(twPanel2);
		

	}

	private void customizeEditorPane() {
		editorPane.setContentType("text/html");
		editorPane.setOpaque(false);
		editorPane.setEditable(false);
		scrollPane.setOpaque(false);
		scrollPane.setBorder(BorderFactory.createLoweredSoftBevelBorder());
	}

	private void showPanelBorders() {
		this.setBorder(BorderFactory.createTitledBorder("MAIN PANEL"));
		
		cardPanel1.setBorder(BorderFactory.createTitledBorder("CARD PANEL 1"));
		cardPanel2.setBorder(BorderFactory.createTitledBorder("CARD PANEL 2"));
		cardContainer.setBorder(BorderFactory.createTitledBorder("CARD CONTAINER"));
		
		twPanel1.setBorder(BorderFactory.createTitledBorder("TW PANEL 1"));
		twContainer.setBorder(BorderFactory.createTitledBorder("TW Container"));
		midEmpty.setBorder(BorderFactory.createTitledBorder("MIDEMPTY"));
		scrollPane.setBorder(BorderFactory.createTitledBorder("SP"));
	}

	private void customizeTWPanel() {
		
//		JScrollBar bar = scrollPane.getVerticalScrollBar();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		twContainer.setLayout(new BorderLayout());
		twContainer.add(scrollPane, BorderLayout.CENTER);
		twContainer.setOpaque(false);
		

		twPanel1.setLayout(new BoxLayout(twPanel1, BoxLayout.X_AXIS));
		twPanel1.add(Box.createHorizontalStrut(10));
		twPanel1.add(twContainer);
		twPanel1.add(Box.createHorizontalStrut(10));
		twPanel1.setOpaque(false);
		
		twPanel2.setLayout(new BoxLayout(twPanel2, BoxLayout.Y_AXIS));
		twPanel2.add(twPanel1);
		twPanel2.add(Box.createVerticalStrut(10));
		twPanel2.setOpaque(false);
		
	}

	private void customizeCardPanel() {
		
		cardContainer.setOpaque(false);
		cardContainer.setBorder(b1);
		
		cardPanel2.setLayout(new BoxLayout(cardPanel2, BoxLayout.Y_AXIS));
		cardPanel2.add(Box.createVerticalStrut(50));
		cardPanel2.add(cardPanel1);

		cardPanel1.setLayout(new BoxLayout(cardPanel1, BoxLayout.X_AXIS));
		cardPanel1.add(Box.createHorizontalGlue());
		cardPanel1.add(Box.createHorizontalStrut(100));
		cardPanel1.add(cardContainer);
		cardPanel1.add(Box.createHorizontalStrut(100));
		cardPanel1.add(Box.createHorizontalGlue());
		
		cardPanel1.setOpaque(false);
		cardPanel2.setOpaque(false);
		cardContainer.setOpaque(false);
		
	}

	/**
	 * recieves a Card object which is then displayed in a panel
	 * 
	 * @param cardContainer
	 *            - the card to show
	 */
	public void showCard(Card cardToShow) {
		cardContainer.removeAll();
		cardToShow.setBorder(CustomGui.highlightBorder);
		cardContainer.setPreferredSize(new Dimension(cardToShow.getPreferredSize().width, cardToShow.getPreferredSize().height));
		cardContainer.add(cardToShow);

	}
	
	public static void setText( final String txt )  {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				stringBuilder = new StringBuilder(pre);
				append(txt);
			}
		});
	}

	public static synchronized void  append( final String txt ) {
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
				
				stringBuilder.append("<body style='font-family: ");
				stringBuilder.append(font);
				stringBuilder.append("'>");
				stringBuilder.append(txt+"<br>");
				editorPane.setText(stringBuilder.toString());
//			}
//		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
	}
	
}
