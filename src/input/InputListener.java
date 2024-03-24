package input;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputListener implements MouseMotionListener,MouseListener,KeyListener,FocusListener{
	private final int[] mousePosition;
	private final boolean[] mouseButtons;
	private final boolean[] keys;
	private boolean focus;
	
	public InputListener() {
		this.mousePosition = new int[2];
		this.mouseButtons = new boolean[4];
		this.keys = new boolean[256];
	}
	
	//Getters and setters
	
	public boolean isFocus() {
		return focus;
	}

	public void setFocus(boolean focus) {
		this.focus = focus;
	}

	public int[] getMousePosition() {
		return mousePosition;
	}

	public boolean[] getMouseButtons() {
		return mouseButtons;
	}

	public boolean[] getKeys() {
		return keys;
	}
	
	//Implementation
	

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.mouseButtons[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.mouseButtons[e.getButton()] = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.mousePosition[0] = e.getX();
		this.mousePosition[1] = e.getY();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		this.keys[e.getKeyCode()&0xff] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.keys[e.getKeyCode()&0xff] = false;
	}

	@Override
	public void focusGained(FocusEvent e) {
		this.focus = true;
	}

	@Override
	public void focusLost(FocusEvent e) {
		this.focus = false;
	}
}
