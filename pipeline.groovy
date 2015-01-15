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
    downStream
  }
  previousJob.downstream(j)
  previousJob = j
}

void functional() {
  def j = job {
    name "$projectName-functional"
  }
  previousJob.downstream(j)
  previousJob = j
}

void publish() {
  def j = job {
    name "$projectName-publish"
  }
  previousJob.downstream(j)
  previousJob = j
}

String projectName() {
  new File('.').canonicalFile.name
}
