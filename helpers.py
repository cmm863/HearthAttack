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
def parseTag(line):
  return line.split("tag=",1)[1].split(" value=",1)[0]
def parseZone(line):
    return line.split(" to ",1)[1]
def parse_id(line):
    return line.split("id=",1)[1].split(" ",1)[0]
def parse_entity(line):
    return line.split("Entity=",1)[1].split(" tag",1)[0]
def parse_value(line):
    return line.split("value=",1)[1].strip('\n')
def parse_player_id(line):
    return line.split("player=",1)[1].split("]")[0]
def getSeek(logfile):
  logfile.seek(0,2)
  line = ''
  count = 0
  while True:
    pos = logfile.tell()
    if pos == 0:
      break
    logfile.seek(-1, 1)
    if logfile.read(1) == '\n':
      line = logfile.readline()
      logfile.seek(pos-1)
    else:
      logfile.seek(-1,1)
    if ("Unloading" in line and "Unused Serialized files" in line):
      count+=1
    if count > 2:
      break
  return logfile.tell()


def updateEntity(line):
  ret = line.split("Entity=[", 1)[1].split("] tag=", 1)[0]
  return entitySplit(ret)

def moveEntity(line):
  ret = line.split("card [", 1)[1].split("] to ", 1)[0]
  return entitySplit(ret)

def entitySplit(line):
  args = line.split(" ")
  ret = []
  for arg in args:
    ret.append(arg.partition("=")[2])
  return ret

  