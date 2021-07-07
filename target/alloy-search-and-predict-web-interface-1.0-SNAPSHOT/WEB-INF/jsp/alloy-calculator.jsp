<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<div>
    <a href="/logout">Log out</a>
</div>

<div>
    <h2>Alloy Search and Predict</h2>

    <p>
        <table>
            <tr><td align="right"><b>Authors:</b></td><td><b>D. J. M. King, A. G. McGregor</b></td></tr>
            <tr><td align="right"><b>Email:</b></td><td><b>daniel.miks AT live.com</b></td></tr>
        </table>
    </p>

    <form action="/results.csv">
        <p>
            <table>
                <tr>
                    <td align="right">Optimisation parameter:</td>
                    <td>
                        <select name="optimisationParameter">
                            <c:forEach var="item" items="${outputParameters}">
                                <option value="${item}" ${item == "DELTA" ? 'selected="selected"' : ''}>${item.displayText}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right" value="targetValue">Target value:</td>
                    <td><input type="text" value="0" name="targetValue"/></td>
                </tr>
                <tr>
                    <td align="right" value="temperature">Temperature:</td>
                    <td><input type="text" value="273" name="temperature"/></td>
                    <td>K</td>
                </tr>
            </table>
        </p>

        <p>
            <h3>Search</h3>
            <table>
                <tr>
                    <td align="right">Number of elements:</td>
                    <td>
                        <select name="numberOfElements">
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                            <option>6</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right">Select elements:</td>
                    <td>
                        <select name="element1">
                            <c:forEach var="element" items="${availableElements}">
                                <option value="${element}">${element}</option>
                            </c:forEach>
                        </select>
                        <select name="element2">
                            <c:forEach var="element" items="${availableElements}">
                                <option value="${element}">${element}</option>
                            </c:forEach>
                        </select>
                        <select name="element3" hidden="true">
                            <c:forEach var="element" items="${availableElements}">
                                <option value="${element}">${element}</option>
                            </c:forEach>
                        </select>
                        <select name="element4" hidden="true">
                            <c:forEach var="element" items="${availableElements}">
                                <option value="${element}">${element}</option>
                            </c:forEach>
                        </select>
                        <select name="element5" hidden="true">
                            <c:forEach var="element" items="${availableElements}">
                                <option value="${element}">${element}</option>
                            </c:forEach>
                        </select>
                        <select name="element6" hidden="true">
                            <c:forEach var="element" items="${availableElements}">
                                <option value="${element}">${element}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right">Step size:</td>
                    <td><input type="text" name="stepSize" value="0.2"/></td>
                </tr>
            </table>
            <input type="submit" value="Run"></input>
        </p>

        <p>
            <h3>Specify</h3>
            <textarea name="systems" cols="60" rows="20"></textarea>
            <br>
            <input type="submit" value="Run"/></input>
        </p>

    </form>

</div>
