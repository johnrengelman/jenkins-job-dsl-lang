build {
  test()
  staticAnalysis()
  functional()
  publish()
}

deployments {
  itg()
  cat()
  prod()
}

// Support Methods
// Would be in an a managed lib, not here
def buildJob = null

void build(Closure c) {
  c.resolveStrategy  = Closure.DELEGATE_FIRST
  c.call()
}

void deployments(Closure c) {
  c.resolveStrategy = Closure.DELEGATE_FIRST
  c.call()
}

void itg() {
  def j = job {
    name "$projectName-deploy-itg"
  }
  buildJob.publishers { buildPipelineTrigger(j.name) }
  buildJob = j
}

void cat() {
  def j = job {
    name "$projectNamde-deploy-cat"
  }
  buildJob.publishers { buildPipelineTrigger(j.name) }
  buildJob = j
}

void prod() {
  def j = job {
    name "$projectName-deploy-prod"
  }
  buildJob.publishers { buildPipelineTrigger(j.name) }
}

void test() {
  buildJob = job {
    name "$projectName-test"
  }
}

void staticAnalysis() {
  def j = job {
    name "$projectName-staticAnalysis"
  }
  buildJob.publishers { downstream(j.name) }
  buildJob = j
}

void functional() {
  def j = job {
    name "$projectName-functional"
  }
  buildJob.publishers { downstream(j.name) }
  buildJob = j
}

void publish() {
  def j = job {
    name "$projectName-publish"
  }
  buildJob.publishers { downstream(j.name) }
  buildJob = j
}

String getProjectName() {
  new File('.').canonicalFile.name
}
