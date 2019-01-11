@Library('pipeline-library')
import com.github.okulab.libs.*
  def builder = new JenkinsPipelineBootstrap().createBuilder()

  String project = "${PROJECT}"

  String userName = "${IPN}"

  String gitBranch = "${GIT_BRANCH}"

  String version = "${PROJECT_VERSION}"

  String emailAddress = "${NOTIFICATION}"

String dllVersion="${DLL_VERSION}"
// Oozie Job prefix
String jobPrefix="SAMPLE"

  // List of Coordinators to be groovy.deploy separate with ; char
//String workflowList="bq60_spotfire/bq60_spotfire_init"
  String workflowList = "${WORKFLOWLIST}"
// List of Coordinators to be groovy.deploy separate with ; char
//String coordinatorList=""
  String coordinatorList = "${COORDLIST}"

def Prod=['HDP-2.6':[
  'destinationPath' : "/app/${ENVIRONMENT}/${PROJECT_IRN_SIA}/${OPS_FOLDER_TO_DEPLOY}/${version}/",
  'rootPath' : "dist",
  'conf_module':"${OPS_FOLDER_TO_DEPLOY}",
  'input_path':"/data/transit/${PROJECT_IRN_SIA}/input/manual",
  'output_path':"/data/raw/${PROJECT_IRN_SIA}/ToBeComplete",
  'archive_path':"/archive/${PROJECT_IRN_SIA}/ToBeComplete",
  'working_path':"ToBeComplete",
  'hive_db':"db_raw_${PROJECT_IRN_SIA}",
  'oozieQueue':"oozie_prod",
  'sparkQueue':"${QUEUE_NAME}"
]
]

def Work=['HDP-2.6':[
  'destinationPath' : "/app/${ENVIRONMENT}/${PROJECT_IRN_SIA}/${OPS_FOLDER_TO_DEPLOY}/${version}/",
  'rootPath' : "dist",
  'conf_module':"${OPS_FOLDER_TO_DEPLOY}",
  'input_path':"/data/transit/${PROJECT_IRN_SIA}/input/ToBeComplete",
  'output_path':"/data/raw/${PROJECT_IRN_SIA}/ToBeComplete",
  'archive_path':"/archive/${PROJECT_IRN_SIA}/ToBeComplete",
  'working_path':"ToBeComplete",
  'hive_db':"db_work_${PROJECT_IRN_SIA}",
  'oozieQueue':"oozie_staging",
  'sparkQueue':"work"
]
]


def QA=['HDP-2.6':[
  'destinationPath' : "/app/${ENVIRONMENT}/${PROJECT_IRN_SIA}/${OPS_FOLDER_TO_DEPLOY}/${version}/",
  'rootPath' : "dist",
  'conf_module':"${OPS_FOLDER_TO_DEPLOY}",
  'input_path':"/data/transit/${PROJECT_IRN_SIA}/input/ToBeComplete",
  'output_path':"/data/raw/${PROJECT_IRN_SIA}/ToBeComplete",
  'archive_path':"/archive/${PROJECT_IRN_SIA}/ToBeComplete",
  'working_path':"ToBeComplete",
  'hive_db':"db_qa_${PROJECT_IRN_SIA}",
  'oozieQueue':"oozie_staging",
  'sparkQueue':"qa"
]
]




def zone="${ZONE}"

/*def target=["raw":["prod":rawProd,
                   "work":rawWork,
                   "qa":rawQA],
            "gold":["prod":goldProd,
                    "work":goldWork,
                    "qa":goldQA]
]*/

def target=["raw":["prod":Prod,
                   "work":Work,
                   "qa":QA],
            "gold":["prod":Prod,
                    "work":Work,
                    "qa":QA]
]
println("---------------------------TARGET CLUSTER PROPERTIES: ${TARGET_CLUSTER}----------------------------")
println(target["${if(zone=="RAW")"raw" else "gold"}"]["${ENVIRONMENT}"]["${TARGET_CLUSTER}"])
println("---------------------------START----------------------------")
boolean  skipCoverageAnalisys=true
boolean skipCodeStyleAnalsys=true
boolean deployOnNexus=false
boolean skipConfDeployment=false
boolean killJob=false
if ("${SKIP_CONF_DEPLOYMENT}" == "true") skipConfDeployment = true
if ("${I_WANT_TO_KILL_AN_OOZIE_JOB}" == "true")killJob=true

builder.mavenBuildPushArtifactAndRunOnHDFS("${TARGET_CLUSTER}",project,
  target["${if(zone=="RAW")"raw" else "gold"}"]["${ENVIRONMENT}"]["${TARGET_CLUSTER}"]['conf_module'],version,
  target["${if(zone=="RAW")"raw" else "gold"}"]["${ENVIRONMENT}"]["${TARGET_CLUSTER}"]['destinationPath'],
  emailAddress,"${IPN}",target["${if(zone=="RAW")"raw" else "gold"}"]["${ENVIRONMENT}"]["${TARGET_CLUSTER}"]['rootPath']
  ,jobPrefix,workflowList,
  coordinatorList,dllVersion,skipCoverageAnalisys,skipCodeStyleAnalsys,deployOnNexus,gitBranch,
  target["${if(zone=="RAW")"raw" else "gold"}"]["${ENVIRONMENT}"]["${TARGET_CLUSTER}"]['input_path'],
  target["${if(zone=="RAW")"raw" else "gold"}"]["${ENVIRONMENT}"]["${TARGET_CLUSTER}"]['output_path'],
  target["${if(zone=="RAW")"raw" else "gold"}"]["${ENVIRONMENT}"]["${TARGET_CLUSTER}"]['archive_path'],
  target["${if(zone=="RAW")"raw" else "gold"}"]["${ENVIRONMENT}"]["${TARGET_CLUSTER}"]['working_path'],
  target["${if(zone=="RAW")"raw" else "gold"}"]["${ENVIRONMENT}"]["${TARGET_CLUSTER}"]['hive_db'],
  target["${if(zone=="RAW")"raw" else "gold"}"]["${ENVIRONMENT}"]["${TARGET_CLUSTER}"]['oozieQueue'],
  target["${if(zone=="RAW")"raw" else "gold"}"]["${ENVIRONMENT}"]["${TARGET_CLUSTER}"]['sparkQueue'],skipConfDeployment,
  "2100-05-20'T'10:10'Z'","${ENVIRONMENT}",killJob)

println("---------------------------END----------------------------")
println "DONE !"

