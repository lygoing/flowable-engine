/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flowable.cmmn.engine.impl.cmd;

import org.flowable.cmmn.api.repository.CaseDefinition;
import org.flowable.cmmn.engine.impl.deployer.CmmnDeploymentManager;
import org.flowable.cmmn.engine.impl.util.CommandContextUtil;
import org.flowable.cmmn.model.CmmnModel;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;

/**
 * @author Joram Barrez
 */
public class GetCmmnModelCmd implements Command<CmmnModel> {

    protected String caseDefinitionId;

    public GetCmmnModelCmd(String caseDefinitionId) {
        this.caseDefinitionId = caseDefinitionId;
    }

    public CmmnModel execute(CommandContext commandContext) {
        if (caseDefinitionId == null) {
            throw new FlowableIllegalArgumentException("caseDefinitionId is null");
        }
        
        CmmnDeploymentManager deploymentManager = CommandContextUtil.getCmmnEngineConfiguration(commandContext).getDeploymentManager();
        CaseDefinition caseDefinition = deploymentManager.findDeployedCaseDefinitionById(caseDefinitionId);
        if (caseDefinition != null) {
            return deploymentManager.resolveCaseDefinition(caseDefinition).getCmmnModel();
        }
        return null;
    }
}