package engine;

import java.util.Stack;

/**
 * Crée un system d'annuler/refaire générique
 * @author soulierc
 *
 * @param <T> Type générique représentant les objets à manipuler.
 */
public class UndoRedo<T> {

	private Stack<T> annuler;
	private Stack<T> refaire;

	public UndoRedo() {
		this.annuler = new Stack<T>();
		this.refaire = new Stack<T>();
	}

	public T undo() {
		T r = annuler.pop();
		refaire.add(r);
		return annuler.peek();
	}

	public T redo() {
		T r = refaire.pop();
		annuler.add(r);
		return r;
	}

	public void addItem(T t) {
		annuler.add(t);
	}

	public boolean canRedo() {
		return !refaire.isEmpty();
	}
	

	public boolean canUndo() {
		return !annuler.isEmpty() && annuler.size() >= 2;
	}

}
