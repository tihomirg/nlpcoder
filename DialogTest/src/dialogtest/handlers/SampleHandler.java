package dialogtest.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.window.Window;

import dialogtest.Activator;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public SampleHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		//		MessageDialog.openInformation(
		//				window.getShell(),
		//				"DialogTest",
		//				"Hello, Eclipse world");


		IEditorPart editor =  window.getActivePage().getActiveEditor();

		if (editor instanceof CompilationUnitEditor) {
			CompilationUnitEditor cuEditor = (CompilationUnitEditor) editor;
			ITextViewer viewer = cuEditor.getViewer();

			ISelectionProvider selectionProvider = ((ITextEditor)editor).getSelectionProvider();
			ISelection selection = selectionProvider.getSelection();
			if (selection instanceof TextSelection) {
				TextSelection textSelection = (TextSelection)selection;		    
				int offset = textSelection.getOffset();

				Point caretLocation = viewer.getTextWidget().getCaret().getLocation();
				Point canvasLocation = viewer.getTextWidget().getCaret().getParent().toDisplay(5, -8);

				Point location = new Point(caretLocation.x+canvasLocation.x, caretLocation.y + canvasLocation.y);

				Shell popupSearch = new Shell(window.getShell(), SWT.ON_TOP);
				popupSearch.setSize(600, 150);
				popupSearch.setAlpha(200);
				popupSearch.setText("Expression Search");
				popupSearch.setLocation(location);
				popupSearch.setLayout(new GridLayout(1, true));

				Text searchInput = new Text(popupSearch, SWT.SEARCH);
				searchInput.setMessage("search");
				
				searchInput.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER));

				List snippets = new List(popupSearch, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
				snippets.setLayoutData(new GridData(GridData.FILL_HORIZONTAL  | GridData.VERTICAL_ALIGN_CENTER)); 			
				
				
				for (int i = 0; i < 5; i++) {
					snippets.add("");				
				}
				snippets.redraw();
				ResultListener resultListener = new ResultListener(popupSearch, viewer, viewer.getDocument(), offset);
				snippets.addKeyListener(resultListener);

				//Setting listeners
				SearchListener searchListener = new SearchListener(snippets, Activator.getDefault().getExprSynthesizer());
				searchInput.addListener(SWT.DefaultSelection, searchListener);

				popupSearch.open();
			}

		}


		return null;
	}

	public Object execute2(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		//		MessageDialog.openInformation(
		//				window.getShell(),
		//				"DialogTest",
		//				"Hello, Eclipse world");

		InputDialog dialog = new InputDialog(window.getShell(), "Expression Search", "", "", null);

		if(dialog.open() == Window.OK){
			String input = dialog.getValue();
			IEditorPart editor =  window.getActivePage().getActiveEditor();

			if (editor instanceof CompilationUnitEditor) {
				CompilationUnitEditor cuEditor = (CompilationUnitEditor) editor;
				ITextViewer viewer = cuEditor.getViewer();

				IDocument doc = viewer.getDocument();

				ISelectionProvider selectionProvider = ((ITextEditor)editor).getSelectionProvider();
				ISelection selection = selectionProvider.getSelection();

				if (selection instanceof TextSelection) {
					TextSelection textSelection = (TextSelection)selection;		    
					int offset = textSelection.getOffset();

					try {
						doc.replace(offset, 0, input);

						int cursor = offset + input.length();
						viewer.setSelectedRange(cursor, 0);
						viewer.revealRange(cursor, 0);

					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		return null;
	}
}
