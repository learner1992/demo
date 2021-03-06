package com.maplecloudy.mapps.branch;

import java.util.HashMap;

import org.spark_project.guava.collect.Maps;

import com.maplecloudy.app.MAppRunner;
import com.maplecloudy.app.MAppTool;
import com.maplecloudy.app.annotation.Action;
import com.maplecloudy.app.utils.MAppUtils;

@Action
public class BranchThree implements MAppTool {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  public static void main(String[] args) throws Exception {
    System.exit(MAppRunner.run(new BranchThree(), args));
  }
  
  @Override
  public int run(String[] args) throws Exception {
    System.out.println("*************分支Three程序启动**********");
    String parameter = MAppUtils.getParameter("branchThree", "branchThree");
    System.out.println(
        "*************branchThree获得的输入为（默认是branchThree）**********" + parameter);
    HashMap<String,String> newHashMap = Maps.newHashMap();
    newHashMap.put("selector", "branchThree");
    MAppUtils.savePipelineOutput(newHashMap);
    return 0;
  }
}
