mv .git/objects/pack/*  for i in /*.pack; do   git unpack-objects -r < $i end rm /* 
