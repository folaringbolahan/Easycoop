
                    <h5 class="leftpanel-title">Navigation</h5>
                    <ul class="nav nav-pills nav-stacked">
                        <li class="active"><a href="index.htm"><i class="fa fa-home"></i> <span>Dashboard</span></a></li>
                       <!-- <li><a href="#"><span class="pull-right badge">5</span><i class="fa fa-money"></i> <span>Logs</span></a></li>-->
                       <!-- <li class="parent"><a href="#"><i class="fa fa-suitcase"></i> <span>Transactions</span></a>
                            <ul class="children">
                                <li><a href="modals.html">Daily (Modal Box)</a></li>
                                <li><a href="#">Weekly</a></li>
                                <li><a href="#">MOnthly</a></li>

                            </ul>
                        </li>
                        <li class="parent"><a href="#"><i class="fa fa-bank"></i> <span>General Ledger</span></a>
                            <ul class="children">
                                <li><a href="<%=request.getContextPath()%>/gl/gl_accountgrouplist.htm">Account Group setup</a></li>
                                <li><a href="<%=request.getContextPath()%>/gl/gl_accountseg.htm">Account Segment setup</a></li>
                                <li><a href="<%=request.getContextPath()%>/gl/gl_accountstructlist.htm">Account Structure setup</a></li>
                                <li><a href="<%=request.getContextPath()%>/gl/gl_account.htm">Account setup</a></li>
                                <li><a href="<%=request.getContextPath()%>/gl/gl_accounttxnview.htm">Account Transaction View</a></li>
                                <li><a href="<%=request.getContextPath()%>/gl/gl_journalzk.htm">Post Journal</a></li>
                                
                            </ul>
                        </li>
                        <li class="parent"><a href="#"><i class="fa fa-bank"></i> <span>Forms</span></a>
                            <ul class="children">
                                <li><a href="general-forms.html">General Forms</a></li>
                                <li><a href="form-layouts.html">Layouts</a></li>
                                <li><a href="form-validation.html">Validation</a></li>
                                <li><a href="form-wizards.html">Wizards</a></li>
                            </ul>
                        </li>
                        <li class="parent"><a href="#"><i class="fa fa-bars"></i> <span>Tables</span></a>
                            <ul class="children">
                                <li><a href="basic-tables.html">Basic Tables</a></li>
                                <li><a href="data-tables.html">Data Tables</a></li>
                            </ul>
                        </li>
                        -->
                        <c:forEach var="usermodule" items="${currrentuserServicex.currmodules}" >
                          <li class="parent"><a href="#"><i class="fa ${usermodule.icon}"></i> <span>${usermodule.description}</span></a>
                              <c:forEach var="usermenu" items="${currrentuserServicex.currmodulemenus}" > 
                                 <c:if test="${usermodule.code==usermenu.module}">
                                   <ul class="children">
                                       <c:choose> 
                                       <c:when test="${usermenu.menucode=='GL7'}">
                                            <c:if test="${currrentuserServicex.currusercompany.procmethod=='MANUAL'}"> 
                                            <li><a href="<%=request.getContextPath()%>${usermenu.menupath}">${usermenu.displaytext}</a></li>
                                           </c:if>
                                       </c:when>
                                       <c:when test="${usermenu.menucode=='GL8'}">
                                           <c:if test="${currrentuserServicex.currusercompany.procmethod=='MANUAL'}">
                                            <li><a href="<%=request.getContextPath()%>${usermenu.menupath}">${usermenu.displaytext}</a></li>
                                           </c:if>
                                       </c:when>    
                                       <c:otherwise>    
                                        <li><a href="<%=request.getContextPath()%>${usermenu.menupath}">${usermenu.displaytext}</a></li>
                                       </c:otherwise>
                                       </c:choose>
                                   </ul>
                                 </c:if>
                             </c:forEach>
                           </li>
                       </c:forEach>


                    </ul>
