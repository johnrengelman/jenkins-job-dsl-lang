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

void build(Closure c) {
  c.resolveStrategy  = Closure.DELEGATE_FIRST
  def previousJob = null
  c.call()
}

void test() {
  previousJob = job {
    name "$projectName-test"
  }
}

void staticAnalysis() {
  def j = job {
    name "$projectName-staticAnalysis"
  }
  previousJob.publishers { downstream(j.name) }
  previousJob = j
}

void functional() {
  def j = job {
    name "$projectName-functional"
  }
  previousJob.publishers { downstream(j.name) }
  previousJob = j
}

void publish() {
  def j = job {
    name "$projectName-publish"
  }
  previousJob.publishers { downstream(j.name) }
  previousJob = j
}

String getProjectName() {
  new File('.').canonicalFile.name
}
