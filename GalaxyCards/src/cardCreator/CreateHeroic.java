package cardCreator;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Creates a panel that lets you customize a Heroic card.
 * @author Jonte
 *
 */
public class CreateHeroic extends JPanel {
	private JPanel gridHeroic = new JPanel();
	
	private JTextField tfCardName = new JTextField();
	private JTextField tfPrice = new JTextField();
	private JTextField tfDefense = new JTextField();
	private JTextField tfRarity = new JTextField("Common/Rare/Legendary");
	private JTextField tfNbrOfCards = new JTextField();
	private JCheckBox btnAbility = new JCheckBox();
	private JTextArea taDescription = new JTextArea();

	private JLabel lblCardName = new JLabel("Card Name: ");
	private JLabel lblPrice = new JLabel("Price: ");
	private JLabel lblAbility = new JLabel("Ability: Yes/No");
	private JLabel lblDefense = new JLabel("Defense: ");
	private JLabel lblRarity = new JLabel ("Rarity: ");
	private JLabel lblNbrOfCards = new JLabel("Number of Cards: ");
	private JLabel lblDescription = new JLabel("Description");
	
	
	/**
	 * adds the components in a GridLayout panel and adds that panel to the main panel
	 */
	public CreateHeroic(){
		gridHeroic.setLayout(new GridLayout(7,2));
		gridHeroic.setPreferredSize(new Dimension(400,600));
		gridHeroic.add(lblCardName);
		gridHeroic.add(tfCardName);
		gridHeroic.add(lblDefense);
		gridHeroic.add(tfDefense);
		gridHeroic.add(lblPrice);
		gridHeroic.add(tfPrice);
		gridHeroic.add(lblRarity);
		gridHeroic.add(tfRarity);
		gridHeroic.add(lblAbility);
		gridHeroic.add(btnAbility);
		gridHeroic.add(lblDescription);
		gridHeroic.add(taDescription);
		gridHeroic.add(lblNbrOfCards);
		gridHeroic.add(tfNbrOfCards);
		add(gridHeroic);
	}
	
	public int getPrice() {
		// TODO: Error Handling
		return Integer.parseInt(tfPrice.getText());
	}
	
	public int getDefense() {
		// TODO: Error Handling
		return Integer.parseInt(tfDefense.getText());
	}
	
	public String getRarity() {
		// TODO: Error Handling
		return tfRarity.getText();
	}
	
	public String getName() {
		// TODO: Error Handling
		return tfCardName.getText();
	}
	
	public String getDescription() {
		return taDescription.getText();
	}
}
