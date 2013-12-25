package com.sunny.imessage.push.view;

import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.internal.part.NullEditorInput;
import org.eclipse.ui.part.ViewPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunny.imessage.push.Activator;
import com.sunny.imessage.push.editor.SendEditor;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class NaviView extends ViewPart {

	public final static String ID = "com.sunny.imessage.push.view.NaviView";

	private TreeViewer treeViewer;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public NaviView() {
	}

	@Override
	public void createPartControl(Composite composite) {
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));

		treeViewer = new TreeViewer(composite, SWT.BORDER);
		final Tree tree = treeViewer.getTree();
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				TreeItem[] items = tree.getSelection();
				if (items.length > 0) {
					TreeItem item = items[0];
					if (item.getText().equals("文件发送")) {
						IWorkbenchPage page = NaviView.this.getViewSite().getWorkbenchWindow().getActivePage();
						IEditorPart edit = page.getActiveEditor();
						if (edit == null) {
							try {
								page.openEditor(new NullEditorInput(), SendEditor.ID, true);
							} catch (PartInitException e1) {
								logger.error("", e1);
							}
						}
					}

				}
			}
		});
		treeViewer.setContentProvider(new ITreeContentProvider() {

			@Override
			public void dispose() {
				// TODO Auto-generated method stub

			}

			@Override
			public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public Object[] getChildren(Object parent) {
				if (parent.equals("功能")) {
					return new String[] { /* "扫描", */"发送" };
				} else if (parent.equals("扫描")) {
					return new String[] { "区域扫描", "自定义扫描", "文件扫描" };
				} else if (parent.equals("发送")) {
					return new String[] { "文件发送" };
				}
				return new String[0];
			}

			@Override
			public Object[] getElements(Object parent) {
				return getChildren(parent);
			}

			@Override
			public Object getParent(Object arg0) {
				return null;
			}

			@Override
			public boolean hasChildren(Object parent) {
				if (parent.equals("功能") || parent.equals("扫描") || parent.equals("发送"))
					return true;
				else
					return false;
			}
		});
		treeViewer.setLabelProvider(new LabelProvider() {

			@Override
			public String getText(Object element) {
				return element.toString();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
			 */
			@Override
			public Image getImage(Object element) {
				URL url = null;
				if (element.toString().equals("扫描") || element.toString().equals("发送")) {
					url = Activator.getDefault().getBundle().getResource("icons/apple_folder.png");
				} else {
					url = Activator.getDefault().getBundle().getEntry("icons/apple_menu_2.png");
				}
				return ImageDescriptor.createFromURL(url).createImage();
			}

		});
		treeViewer.setInput("功能");
		treeViewer.expandAll();

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
