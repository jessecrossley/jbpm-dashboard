/**
 * Copyright (C) 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jbpm.dashboard;

import org.jboss.dashboard.annotation.Priority;
import org.jboss.dashboard.annotation.Startable;
import org.jboss.dashboard.annotation.config.Config;
import org.jboss.dashboard.initialModule.InitialModuleRegistry;
import org.jboss.dashboard.kpi.KPIInitialModule;
import org.jboss.dashboard.workspace.export.ImportWorkspacesModule;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Class that builds the jBPM dashboard on startup.
 */
@ApplicationScoped
public class jBPMDashboardBuilder implements Startable {

    @Inject
    private InitialModuleRegistry initialModuleRegistry;

    @Inject @Config("WEB-INF/etc/appdata/initialData/jbpmKPIs.xml")
    private String kpiFile;

    @Inject @Config("WEB-INF/etc/appdata/initialData/jbpmWorkspace.xml")
    private String workspaceFile;

    public Priority getPriority() {
        return Priority.NORMAL;
    }

    public void start() {
        KPIInitialModule kpis = new KPIInitialModule();
        kpis.setName("org.jboss.dashboard.jbpm.jbpmKPIs");
        kpis.setImportFile(kpiFile);
        kpis.setVersion(1);
        initialModuleRegistry.registerInitialModule(kpis);

        ImportWorkspacesModule workspace = new ImportWorkspacesModule();
        workspace.setName("org.jboss.dashboard.jbpm.jbpmWorkspace");
        workspace.setImportFile(workspaceFile);
        workspace.setVersion(1);
        initialModuleRegistry.registerInitialModule(workspace);
    }
}
