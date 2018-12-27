package application;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class StackDev extends AnAction {


    @Override
    public void actionPerformed(AnActionEvent event) {
        // Get the required data from data keys
        final Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = editor.getProject();
        final Document document = editor.getDocument();
        final SelectionModel selectionModel = editor.getSelectionModel();
        StackApi stackApi = new StackApi(project);

        String selectedText = selectionModel.getSelectedText();
        stackApi.getAnswers(selectedText);
        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();
        // Make the replacement
        WriteCommandAction.runWriteCommandAction(project, () ->
                document.replaceString(start, end, "<PLACEHOLDER - do stack overflow api call here>")
        );
        selectionModel.removeSelection();
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        final Project project = e.getProject();
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        // Set visibility only in case of existing project and editor and if some text in the editor is selected
        e.getPresentation().setVisible(project != null && editor != null && editor.getSelectionModel().hasSelection());
    }
}
