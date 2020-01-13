package pictures.taking.washing.customComponents;

import org.primefaces.component.tree.Tree;
import org.primefaces.component.tree.UITreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.renderkit.RendererUtils;
import org.primefaces.util.HTML;

import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.List;

public class MyTreeRenderer extends org.primefaces.component.tree.TreeRenderer {
    protected void encodeDropTarget(FacesContext context, Tree tree) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        writer.startElement("li", null);
        writer.writeAttribute("data-type", "event", null);
        writer.writeAttribute("class", "ui-tree-droppoint", null);
        //create the ADD button
        encodeButton(context, "", MyTree.ADD_BUTTON_CLASS, MyTree.ADD_BUTTON_ICON_CLASS);
        writer.endElement("li");
    }

    protected void encodeButtonSpacer(FacesContext context, Tree tree) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        writer.startElement("li", null);
        writer.writeAttribute("data-type", "event", null);
        writer.writeAttribute("class", "ui-droppable", null);

        //create the ADD button
        encodeButton(context, "", MyTree.ADD_BUTTON_CLASS, MyTree.ADD_BUTTON_ICON_CLASS);
        writer.endElement("li");
    }

    protected void encodeDropTargetSpacer(FacesContext context, Tree tree) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("li", null);
        writer.writeAttribute("data-type", "section", null);
        writer.writeAttribute("class", "ui-tree-droppoint ui-section-droppoint ui-droppable", null);
        encodeButton(context, "", MyTree.ADD_BUTTON_CLASS, MyTree.ADD_BUTTON_ICON_CLASS);
        writer.endElement("li");
    }

    protected void encodeButton(FacesContext context, String title, String styleClass, String icon) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        writer.startElement("button", null);
        writer.writeAttribute("type", "button", null);
        writer.writeAttribute("class", HTML.BUTTON_ICON_ONLY_BUTTON_CLASS + " " + styleClass, null);
        writer.writeAttribute("title", title, null);

        //icon
        writer.startElement("span", null);
        writer.writeAttribute("class", icon, null);
        writer.endElement("span");

        //text
        writer.startElement("span", null);
        writer.writeAttribute("class", HTML.BUTTON_TEXT_CLASS, null);
        writer.write("ui-button");
        writer.endElement("span");

        writer.endElement("button");
    }

    @Override
    public void encodeTreeNode(FacesContext context, Tree tree, TreeNode node, String clientId, boolean dynamic, boolean checkbox, boolean dragdrop) throws IOException {

        //preselection
        String rowKey = node.getRowKey();
        boolean selected = node.isSelected();
        boolean partialSelected = node.isPartialSelected();
        boolean filter = (tree.getValueExpression("filterBy") != null);

        UITreeNode uiTreeNode = tree.getUITreeNodeByType(node.getType());
        if (!uiTreeNode.isRendered()) {
            return;
        }

        List<String> filteredRowKeys = tree.getFilteredRowKeys();
        boolean match = false;
        if (filter && filteredRowKeys.size() > 0) {
            for (String filteredRowKey : filteredRowKeys) {
                String rowKeyExt = rowKey + "_";
                String filteredRowKeyExt = filteredRowKey + "_";
                if (filteredRowKey.startsWith(rowKeyExt) || rowKey.startsWith(filteredRowKeyExt) || filteredRowKey.equals(rowKey)) {
                    match = true;
                    if (!node.isLeaf() && !rowKey.startsWith(filteredRowKey)) {
                        node.setExpanded(true);
                    }
                    break;
                }
            }

            if (!match) {
                return;
            }
        }

        ResponseWriter writer = context.getResponseWriter();
        tree.setRowKey(rowKey);
        boolean isLeaf = node.isLeaf();
        boolean expanded = node.isExpanded();
        boolean selectable = tree.getSelectionMode() != null && node.isSelectable();
        String toggleIcon = expanded
                ? Tree.EXPANDED_ICON_CLASS_V
                : (tree.isRTLRendering() ? Tree.COLLAPSED_ICON_RTL_CLASS_V : Tree.COLLAPSED_ICON_CLASS_V);
        String stateIcon = isLeaf ? Tree.LEAF_ICON_CLASS : toggleIcon;
        Object datakey = tree.getDatakey();
        String nodeId = clientId + UINamingContainer.getSeparatorChar(context) + rowKey;

        //style class of node
        String containerClass = isLeaf ? Tree.LEAF_NODE_CLASS : Tree.PARENT_NODE_CLASS;

        if (selected) {
            containerClass += " ui-treenode-selected";
        } else if (partialSelected) {
            containerClass += " ui-treenode-hasselected";
        } else {
            containerClass += " ui-treenode-unselected";
        }

        containerClass = uiTreeNode.getStyleClass() == null ? containerClass : containerClass + " " + uiTreeNode.getStyleClass();
        writer.startElement("div", null);
        writer.startElement("li", null);
        writer.writeAttribute("id", nodeId, null);
        writer.writeAttribute("data-rowkey", rowKey, null);
        writer.writeAttribute("data-nodetype", uiTreeNode.getType(), null);
        writer.writeAttribute("class", containerClass, null);

        if (datakey != null) {
            writer.writeAttribute("data-datakey", datakey, null);
        }

        //content
        String contentClass = selectable ? Tree.SELECTABLE_NODE_CONTENT_CLASS_V : Tree.NODE_CONTENT_CLASS_V;
        if (dragdrop) {
            contentClass += " ui-treenode-droppable";
        }

        writer.startElement("span", null);
        writer.writeAttribute("class", contentClass, null);
        writer.writeAttribute("role", "treeitem", null);
        writer.writeAttribute("aria-expanded", String.valueOf(expanded), null);
        writer.writeAttribute("aria-selected", String.valueOf(selected), null);
        if (checkbox) {
            writer.writeAttribute("aria-checked", String.valueOf(selected), null);
        }

        //state icon
        writer.startElement("span", null);
        writer.writeAttribute("class", stateIcon, null);
        writer.endElement("span");

        //checkbox
        if (checkbox) {
            RendererUtils.encodeCheckbox(context, selected, partialSelected, !selectable, Tree.CHECKBOX_CLASS);
        }

        //node icon
        encodeIcon(context, uiTreeNode, expanded);

        //label
        String nodeLabelClass = selected ? Tree.NODE_LABEL_CLASS + " ui-state-highlight" : Tree.NODE_LABEL_CLASS;

        writer.startElement("span", null);
        writer.writeAttribute("class", nodeLabelClass, null);
        if (!tree.isDisabled()) {
            writer.writeAttribute("tabindex", "-1", null);
        }

        writer.writeAttribute("role", "treeitem", null);
        writer.writeAttribute("aria-label", uiTreeNode.getAriaLabel(), null);
        uiTreeNode.encodeAll(context);
        writer.endElement("span");

        writer.endElement("span");

        //children nodes
        writer.startElement("ul", null);
        writer.writeAttribute("class", Tree.CHILDREN_NODES_CLASS, null);
        writer.writeAttribute("role", "group", null);

        if (!expanded) {
            writer.writeAttribute("style", "display:none", null);
        }

        if ((dynamic && expanded) || !dynamic) {
            encodeTreeNodeChildren(context, tree, node, clientId, dynamic, checkbox, dragdrop);
        }

        writer.endElement("ul");

        writer.endElement("li");
        //end of Children Nodes

        if (dragdrop && (isLeaf && !node.getType().equals("section"))) {
            encodeDropTarget(context, tree);
        } else if (dragdrop) {
            encodeDropTargetSpacer(context, tree);
        }
        writer.endElement("div");

    }


    @Override
    public void encodeTreeNodeChildren(FacesContext context, Tree tree, TreeNode node, String clientId, boolean dynamic, boolean checkbox, boolean droppable) throws IOException {

        int childCount = node.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                if (i == 0 && droppable) {
                    if (node.getRowKey().equals("root")) {
                        encodeDropTargetSpacer(context, tree);
                    } else {
                        encodeDropTarget(context, tree);
                    }
                }

                encodeTreeNode(context, tree, node.getChildren().get(i), clientId, dynamic, checkbox, droppable);
            }
        } else {
            if (node.getRowKey().equals("root")) {
                encodeDropTargetSpacer(context, tree);
            } else {
                encodeDropTarget(context, tree);
            }
        }

    }
}
