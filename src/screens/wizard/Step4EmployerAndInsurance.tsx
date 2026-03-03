import React from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  SafeAreaView,
  ScrollView,
  Linking,
  Alert,
} from 'react-native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { RouteProp } from '@react-navigation/native';
import { RootStackParamList, KRANKENKASSEN } from '../../types';
import WizardProgress from '../../components/WizardProgress';

type Props = {
  navigation: NativeStackNavigationProp<RootStackParamList, 'Step4EmployerAndInsurance'>;
  route: RouteProp<RootStackParamList, 'Step4EmployerAndInsurance'>;
};

function openUrl(url: string) {
  Linking.canOpenURL(url)
    .then((supported) => {
      if (supported) {
        Linking.openURL(url);
      } else {
        Alert.alert('Link öffnen', `Bitte besuchen Sie: ${url}`);
      }
    })
    .catch(() => Alert.alert('Fehler', 'Der Link konnte nicht geöffnet werden.'));
}

export default function Step4EmployerAndInsurance({ navigation, route }: Props) {
  const { isSingleParent, krankenkasse } = route.params;
  const kkData = KRANKENKASSEN.find((k) => k.id === krankenkasse);

  const daysPerChild = isSingleParent ? 20 : 10;
  const maxDaysTotal = isSingleParent ? 50 : 25;

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView contentContainerStyle={styles.content}>
        <WizardProgress currentStep={4} totalSteps={4} />

        <Text style={styles.stepLabel}>Schritt 4 von 4</Text>
        <Text style={styles.title}>Arbeitgeber & Krankenkasse</Text>
        <Text style={styles.description}>
          Jetzt müssen Sie Ihren Arbeitgeber informieren und das Kinderkrankengeld bei
          Ihrer Krankenkasse beantragen.
        </Text>

        {/* Employer section */}
        <View style={styles.sectionCard}>
          <Text style={styles.sectionTitle}>👔 1. Arbeitgeber informieren</Text>
          <Text style={styles.sectionText}>
            Teilen Sie Ihrem Arbeitgeber so früh wie möglich mit, dass Sie aufgrund der
            Erkrankung Ihres Kindes zu Hause bleiben müssen.
          </Text>
          <View style={styles.checkList}>
            <Text style={styles.checkItem}>✅ Arbeitgeber telefonisch oder per E-Mail informieren</Text>
            <Text style={styles.checkItem}>✅ Kinderkrankenschein beim Arbeitgeber einreichen</Text>
            <Text style={styles.checkItem}>
              ✅ Arbeitnehmer hat{' '}
              <Text style={styles.bold}>keinen Anspruch auf Lohnfortzahlung</Text> durch den
              Arbeitgeber – die Zahlung erfolgt durch die Krankenkasse
            </Text>
            <Text style={styles.checkItem}>
              ✅ Der Arbeitgeber muss die Fehlzeit{' '}
              <Text style={styles.bold}>genehmigen (unbezahlte Freistellung)</Text>
            </Text>
          </View>
        </View>

        {/* Days entitlement */}
        <View style={styles.daysBox}>
          <Text style={styles.daysTitle}>
            📅 Ihr Anspruch {isSingleParent ? '(Alleinerziehend)' : '(je Elternteil)'}
          </Text>
          <Text style={styles.daysMain}>
            <Text style={styles.daysNumber}>{daysPerChild}</Text> Tage pro Kind pro Jahr
          </Text>
          <Text style={styles.daysSub}>
            Maximal {maxDaysTotal} Kinderkranktage gesamt (bei mehreren Kindern)
          </Text>
          <Text style={styles.daysNote}>Für Kinder unter 12 Jahren (bzw. ohne Altersbegrenzung bei Behinderung)</Text>
        </View>

        {/* Krankenkasse section */}
        <View style={styles.sectionCard}>
          <Text style={styles.sectionTitle}>
            🏥 2. Kinderkrankengeld beantragen{kkData ? ` bei der ${kkData.shortName}` : ''}
          </Text>

          {kkData ? (
            <>
              <Text style={styles.sectionText}>{kkData.digitalSubmissionNote}</Text>

              <View style={styles.checkList}>
                <Text style={styles.checkItem}>
                  ✅ Kinderkrankenschein (Muster 21) einreichen
                </Text>
                <Text style={styles.checkItem}>
                  ✅ Antrag auf Kinderkrankengeld stellen
                </Text>
                <Text style={styles.checkItem}>
                  ✅ Kinderkrankengeld beträgt ca.{' '}
                  <Text style={styles.bold}>90 % des Nettolohns</Text> (max. 90% des
                  Regelentgelts)
                </Text>
              </View>

              <View style={styles.actionButtons}>
                {kkData.onlinePortalUrl && (
                  <TouchableOpacity
                    style={styles.actionButton}
                    onPress={() => openUrl(kkData.onlinePortalUrl!)}
                    accessibilityLabel={`Online-Portal der ${kkData.shortName} öffnen`}
                  >
                    <Text style={styles.actionButtonText}>
                      🌐 Online-Portal {kkData.shortName}
                    </Text>
                  </TouchableOpacity>
                )}
                {kkData.hotline && (
                  <TouchableOpacity
                    style={styles.actionButtonSecondary}
                    onPress={() => openUrl(`tel:${kkData.hotline!.replace(/\s/g, '')}`)}
                    accessibilityLabel={`Hotline der ${kkData.shortName} anrufen`}
                  >
                    <Text style={styles.actionButtonSecondaryText}>
                      📞 Hotline: {kkData.hotline}
                    </Text>
                  </TouchableOpacity>
                )}
              </View>
            </>
          ) : (
            <>
              <Text style={styles.sectionText}>
                Wenden Sie sich direkt an Ihre Krankenkasse und reichen Sie den
                Kinderkrankenschein (Muster 21) ein.
              </Text>
              <View style={styles.checkList}>
                <Text style={styles.checkItem}>✅ Kinderkrankenschein einreichen</Text>
                <Text style={styles.checkItem}>✅ Antrag auf Kinderkrankengeld stellen</Text>
              </View>
            </>
          )}
        </View>

        {/* Done */}
        <View style={styles.doneBox}>
          <Text style={styles.doneTitle}>🎉 Das Wichtigste in Kürze</Text>
          <Text style={styles.doneText}>
            1. Kind krank → Arzt → Kinderkrankenschein{'\n'}
            2. Arbeitgeber informieren & Freistellung beantragen{'\n'}
            3. Kinderkrankengeld bei Krankenkasse beantragen{'\n'}
            4. Krankenkasse zahlt ~90 % des Nettolohns
          </Text>
        </View>

        <TouchableOpacity
          style={styles.restartButton}
          onPress={() => navigation.navigate('Home')}
          accessibilityLabel="Zum Startbildschirm zurückkehren"
        >
          <Text style={styles.restartButtonText}>↩ Zurück zur Startseite</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.specialCasesButton}
          onPress={() => navigation.navigate('SpecialCases')}
          accessibilityLabel="Sonderfälle anzeigen"
        >
          <Text style={styles.specialCasesButtonText}>
            ℹ️  Sonderfälle &amp; Ausnahmen anzeigen
          </Text>
        </TouchableOpacity>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#E6F4FE',
  },
  content: {
    padding: 20,
    paddingBottom: 40,
  },
  stepLabel: {
    fontSize: 13,
    color: '#888',
    textAlign: 'center',
    marginBottom: 4,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#1A5276',
    textAlign: 'center',
    marginBottom: 10,
  },
  description: {
    fontSize: 15,
    color: '#555',
    textAlign: 'center',
    lineHeight: 22,
    marginBottom: 24,
  },
  sectionCard: {
    backgroundColor: '#fff',
    borderRadius: 14,
    padding: 16,
    marginBottom: 14,
    elevation: 2,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.08,
    shadowRadius: 3,
  },
  sectionTitle: {
    fontSize: 17,
    fontWeight: 'bold',
    color: '#1A5276',
    marginBottom: 10,
  },
  sectionText: {
    fontSize: 14,
    color: '#444',
    lineHeight: 21,
    marginBottom: 12,
  },
  checkList: {
    gap: 6,
  },
  checkItem: {
    fontSize: 14,
    color: '#333',
    lineHeight: 21,
  },
  bold: {
    fontWeight: 'bold',
  },
  daysBox: {
    backgroundColor: '#D5F5E3',
    borderRadius: 14,
    padding: 16,
    marginBottom: 14,
    alignItems: 'center',
    borderWidth: 1.5,
    borderColor: '#27AE60',
  },
  daysTitle: {
    fontSize: 15,
    fontWeight: '600',
    color: '#1E8449',
    marginBottom: 8,
  },
  daysMain: {
    fontSize: 16,
    color: '#1E8449',
    fontWeight: '600',
  },
  daysNumber: {
    fontSize: 28,
    fontWeight: 'bold',
    color: '#145A32',
  },
  daysSub: {
    fontSize: 13,
    color: '#2ECC71',
    marginTop: 4,
    fontWeight: '600',
  },
  daysNote: {
    fontSize: 12,
    color: '#555',
    marginTop: 6,
    textAlign: 'center',
  },
  actionButtons: {
    gap: 10,
    marginTop: 12,
  },
  actionButton: {
    backgroundColor: '#2E86AB',
    borderRadius: 10,
    paddingVertical: 13,
    alignItems: 'center',
  },
  actionButtonText: {
    color: '#fff',
    fontSize: 15,
    fontWeight: 'bold',
  },
  actionButtonSecondary: {
    borderRadius: 10,
    paddingVertical: 13,
    alignItems: 'center',
    borderWidth: 1.5,
    borderColor: '#2E86AB',
  },
  actionButtonSecondaryText: {
    color: '#2E86AB',
    fontSize: 15,
    fontWeight: '600',
  },
  doneBox: {
    backgroundColor: '#EBF5FB',
    borderRadius: 12,
    padding: 16,
    marginBottom: 20,
    borderLeftWidth: 4,
    borderLeftColor: '#2E86AB',
  },
  doneTitle: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#1A5276',
    marginBottom: 10,
  },
  doneText: {
    fontSize: 14,
    color: '#333',
    lineHeight: 24,
  },
  restartButton: {
    backgroundColor: '#2E86AB',
    borderRadius: 12,
    paddingVertical: 14,
    alignItems: 'center',
    marginBottom: 12,
  },
  restartButtonText: {
    color: '#fff',
    fontSize: 15,
    fontWeight: 'bold',
  },
  specialCasesButton: {
    borderRadius: 12,
    paddingVertical: 14,
    alignItems: 'center',
    borderWidth: 1.5,
    borderColor: '#2E86AB',
    backgroundColor: '#fff',
  },
  specialCasesButtonText: {
    color: '#2E86AB',
    fontSize: 15,
    fontWeight: '600',
  },
});
