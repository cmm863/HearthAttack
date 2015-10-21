import time
def follow(f):
  f.seek(0,2)
  while True:
    line = f.readline()
    if not line:
      time.sleep(0.1)
      continue
    yield line

def parseName(line):
  return line.split("name=",1)[1].split(" id=",1)[0]
def parseTarget(line):
  return line.split("Target=[name=",1)[1].split(" id=",1)[0]