package guiPacket;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import exceptionsPacket.GuiContainerException;

public class OpponentHandGUI extends JPanel {

	private BoardGuiController boardController;
	private int cardsOnHand = 0, horizontalPosition = 10;
	private JLabel[] cards = new JLabel[8];
	private ImageIcon icon;
	private JLayeredPane layeredPane;

	private final String PATH = "files/pictures/CardBackside.jpg";

	public OpponentHandGUI(BoardGuiController boardController) {
		this.boardController = boardController;
		boardController.addOpponentHandListener(this);

		initiateLayeredPane();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(layeredPane);

	}

	private void initiateLayeredPane() {
		layeredPane = new JLayeredPane();
		layeredPane.setOpaque(true);
		layeredPane.setLayout(null);
		layeredPane.setPreferredSize(new Dimension(730, 240));
		layeredPane.setBorder(BorderFactory.createTitledBorder("Hand"));
	}

	public void drawCard() throws GuiContainerException {
		if (cardsOnHand < 8) {
			JLabel card = new JLabel();
			icon = new ImageIcon(PATH);
			card.setIcon(icon);
			card.setBounds(horizontalPosition, 20, icon.getIconWidth(), icon.getIconHeight());
			cards[cardsOnHand] = card;
			layeredPane.add(card, new Integer(cardsOnHand));
			horizontalPosition += 80;
			cardsOnHand++;
		} else {
			throw new GuiContainerException("Opponent can only have 8 cards in hand");
		}
	}

	public void playCard() throws GuiContainerException {
		if (cardsOnHand > 0) {
			layeredPane.remove(0);
			layeredPane.repaint();
			horizontalPosition -= 80;
			cardsOnHand--;
		}else{
			throw new GuiContainerException("Opponents hand is empty");
		}

	}

}