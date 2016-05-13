package guiPacket;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import cards.HeroicSupport;
import cards.Unit;
import enumMessage.Lanes;
import enumMessage.Phase;

/**
 * A mouselistener class that is added to the units, heroic support and hero gui
 * that are placed on board and belong to the player.
 * 
 * @author 13120dde
 *
 */
public class PlayerTargetMouseListener implements MouseListener {
	private Card card;
	private HeroGUI heroGui;
	private Border cardDefaultBorder;
	private Border highlightB = BorderFactory.createLineBorder(CustomGui.playerColor, 3, true);
	private BoardGuiController boardController;

	/**
	 * The constructor takes a BoardGuiController object as argument.
	 * 
	 * @param boardController
	 *            : BoardGuiController
	 */
	public PlayerTargetMouseListener(BoardGuiController boardController) {
		this.boardController = boardController;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		if (event.getSource() instanceof Card) {

			card = (Card) event.getSource();
			cardDefaultBorder = card.getBorder();
			card.setBorder(BorderFactory.createCompoundBorder(highlightB, cardDefaultBorder));
			if (card instanceof Unit) {
				boardController.updateInfoPanelCard((Unit) card);
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent event) {
		if (event.getSource() instanceof Card) {
			card.setBorder(cardDefaultBorder);
		}
	}

	@Override
	public void mousePressed(MouseEvent event) {
		/*
		 * Different mouseinteraction avaible depending on which phase the
		 * client is in. The possible phases are DEFENDING, ATTACKING, IDLE.
		 * Different mouseinteraction avaible for each object. Units will
		 * attack, heroic support use ability etc.
		 * 
		 */
		if (boardController.getPhase() == Phase.DEFENDING) {
			if (event.getSource() instanceof Unit) {
				card = (Unit) event.getSource();

				if (((Unit) card).getLaneEnum() == Lanes.PLAYER_DEFENSIVE) {
					if (((Unit) card).getTap() == false) {
						boardController.startDefendThreadListener(card);
					} else {
						InfoPanelGUI.append("This unit is tapped, can't defend.");
					}

				} else {
					InfoPanelGUI.append("This unit is not in defensive lane, can't defend");
				}
			}
			if (event.getSource() instanceof HeroicSupport) {
				card = (HeroicSupport) event.getSource();
				if(((HeroicSupport)card).getTap()==false){
					/*kolla om ability behöver target = false
					 * 	skicka kortobjektet till servern
					 *annars
					 *	starta targetthread
					 *	när target är vald
					 *		skicka kortobjektet till servern
					 *tap kortet.
					 */
					if(((HeroicSupport)card).getAbility().hasTarget()==false){
						((HeroicSupport)card).tap();
						boardController.useAbility(card);
						
					}else{
						
					}
				}else{
					InfoPanelGUI.append("This heroic support is tapped, can't use ability.");
				}
			}
		}

		if (boardController.getPhase() == Phase.ATTACKING) {

			if (event.getSource() instanceof Card) {
				card = (Card) event.getSource();
				if (card instanceof Unit) {
					if (((Unit) card).getLaneEnum() == Lanes.PLAYER_OFFENSIVE) {
						if (((Unit) card).getTap() == false) {
							boardController.startAttackThreadListner();
							boardController.setAttacker(card);
						} else {
							InfoPanelGUI.append("The card is tapped, can't attack.");
						}
					} else {
						InfoPanelGUI.append("Invalid move: can't attack with units in defensive lane");
					}
				}
				if (card instanceof HeroicSupport) {
					if (((HeroicSupport) card).getTap()) {
						InfoPanelGUI.append("The card is tapped, can't use ability.");
					} else {
						InfoPanelGUI.append("TODO: use ability: Area of effect / target: world  ");
						if(((HeroicSupport) card).getAbility().hasTarget()){
							
						}else{
							
						}
						((HeroicSupport) card).tap();
					}
				}
			}

			if (event.getSource() instanceof HeroGUI) {
				InfoPanelGUI.append(heroGui.toString());
			}
		}

		if (boardController.getPhase() == Phase.IDLE) {
			InfoPanelGUI.append(event.getSource().toString());
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
