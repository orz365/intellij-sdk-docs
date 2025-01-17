// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package org.intellij.sdk.psi;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtil;

public class PsiNavigationDemoAction extends AnAction {

  @Override
  public void actionPerformed(AnActionEvent anActionEvent) {
    Project project = anActionEvent.getData(CommonDataKeys.PROJECT);
    Editor editor = anActionEvent.getData(CommonDataKeys.EDITOR);
    PsiFile psiFile = anActionEvent.getData(CommonDataKeys.PSI_FILE);
    if (editor == null || psiFile == null) {
      return;
    }
    int offset = editor.getCaretModel().getOffset();

    GlobalSearchScope scope = GlobalSearchScope.allScope(project);
    PsiFile[] psiFiles = FilenameIndex.getFilesByName(project,"tt.xml", scope );
    if(psiFiles.length > 0){
      PsiDirectory psiDirectory = psiFiles[0].getContainingDirectory();
      System.out.println(psiDirectory.toString());
    }

    final StringBuilder infoBuilder = new StringBuilder();
    PsiElement element = psiFile.findElementAt(offset);
    infoBuilder.append("Element at caret: ").append(element).append("\n");
    if (element != null) {
      PsiMethod containingMethod = PsiTreeUtil.getParentOfType(element, PsiMethod.class);
      infoBuilder
              .append("Containing method: ")
              .append(containingMethod != null ? containingMethod.getName() : "none")
              .append("\n");
      if (containingMethod != null) {
        PsiClass containingClass = containingMethod.getContainingClass();
        infoBuilder
                .append("Containing class: ")
                .append(containingClass != null ? containingClass.getName() : "none")
                .append("\n");

        infoBuilder.append("Local variables:\n");
        containingMethod.accept(new JavaRecursiveElementVisitor() {
          @Override
          public void visitLocalVariable(PsiLocalVariable variable) {
            super.visitLocalVariable(variable);
            infoBuilder.append(variable.getName()).append("\n");
          }
        });
      }
    }
    Messages.showMessageDialog(anActionEvent.getProject(), infoBuilder.toString(), "PSI Info", null);
  }

  @Override
  public void update(AnActionEvent e) {
    Editor editor = e.getData(CommonDataKeys.EDITOR);
    PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
    e.getPresentation().setEnabled(editor != null && psiFile != null);
  }

}
