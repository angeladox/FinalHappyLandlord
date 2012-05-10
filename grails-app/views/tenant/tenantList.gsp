<html>
<head>
<title>Tenants for ${landlord.firstName}
</title>
<meta name="layout" content="main" />
</head>
<body>


	<g:if test="${tenantAmt == 0}">
		<h1 style="margin-left: 1.5em;">
			${landlord.firstName}: You currently have
			${tenantAmt}
			tenants. Do you need to
			<g:link controller="tenant" action="create">      
      add one      
              </g:link>
			?
		</h1>
	</g:if>
	<g:else>
		<h1 style="margin-left: 1.5em;">
			Tenants for
			${landlord.firstName}
		</h1>
	</g:else>
	<br>
	<div class="allTens" width="30px">
		<table width="30px" style = "font-size: .75em;">
			<g:each in="${tenants}" var="tenant">
				<g:each in="${tenant}" var="ten">
					<tr width="30px">
						<td>
							Name:
							${ten.firstName}
						${ten.lastName}
						</td>
						<td>
						Email:
						${ten.email}
						</td>
						<td>
						Phone:
						${ten.phone}
						</td>
						<td>
						Credit:
						${ten.creditScore}
						</td>
						<td>
						Previous Address?
						${ten.prevAddress}
						</td>
						<td>
						Previous Landlord?
						${ten.prevLandlord}
						</td>
						<td>
						Previous Landlord Phone?
						${ten.prevLandlordPhone}
						</td>
						<td>
						Comments?
						${ten.comments}
						</td>
						<td>
						Date Created:
						${ten.dateCreated}
						</td>
						</tr>
				</g:each>
			</g:each>
			
		</table>
	</div>
</body>
</html>

